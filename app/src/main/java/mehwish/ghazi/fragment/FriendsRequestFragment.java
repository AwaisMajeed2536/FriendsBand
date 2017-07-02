package mehwish.ghazi.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mehwish.ghazi.R;
import mehwish.ghazi.adapter.FriendRequestsAdapter;
import mehwish.ghazi.adapter.FriendsListAdapter;
import mehwish.ghazi.helper.UtilHelpers;
import mehwish.ghazi.interfaces.CLICKTYPE;
import mehwish.ghazi.interfaces.TwoButtonAlertDialogCallback;
import mehwish.ghazi.model.FriendsListAndRequestModel;
import mehwish.ghazi.model.UserAccountModel;

/**
 * Created by Devprovider on 3/19/2017.
 */

public class FriendsRequestFragment extends Fragment {

    private static final String FRAGMENT_TAG = "FRIENDS_REQUEST_FRAGMENT";
    private Context context;
    private ListView friendRequestLV;
    private List<FriendsListAndRequestModel> dataList = new ArrayList<>();
    private DatabaseReference requestRef;
    private DatabaseReference addFriendRef;
    private String loggedInUserName;
    private List<String> friendsList;
    private List<UserAccountModel> mainDataList = new ArrayList<>();
    private DatabaseReference dataListRef;
    private ProgressDialog progressDialog;

    public FriendsRequestFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend_requests, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Fetching Friend's List");
        progressDialog.setMessage("Please wait...");
        progressDialog.setIcon(R.mipmap.fetching_icon);
        progressDialog.show();
        friendRequestLV = (ListView) view.findViewById(R.id.friend_requests_lv);
        loggedInUserName = UtilHelpers.getLoggedInUser().getEmail().replace(".", "_");
        requestRef = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://friendsband-a3dc9.firebaseio.com/root/requestRecords/" + loggedInUserName);
        addFriendRef = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://friendsband-a3dc9.firebaseio.com/root/friendsRecord/" + loggedInUserName);
        dataListRef = FirebaseDatabase.getInstance().getReferenceFromUrl
                ("https://friendsband-a3dc9.firebaseio.com/root/userData/");
        requestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    progressDialog.dismiss();
                    UtilHelpers.showAlertDialog(getActivity(), "Sorry!","you have no pending friend requests...");
                    return;
                }

                HashMap<String, String> hm = (HashMap<String, String>) dataSnapshot.getValue();
                friendsList = new ArrayList<String>(hm.keySet());
                dataListRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        progressDialog.dismiss();
                        if (dataSnapshot.getValue() == null) {
                            return;
                        }
                        HashMap<String, HashMap<String, String>> data = (HashMap<String, HashMap<String, String>>)
                                dataSnapshot.getValue();
                        for (HashMap.Entry<String, HashMap<String, String>> entry : data.entrySet()) {
                            String check = entry.getKey();
                            HashMap<String, String> obj = entry.getValue();
                            if (friendsList.contains(check)) {
                                mainDataList.add(new UserAccountModel(obj.get("firstName"), obj.get("lastName"),
                                        obj.get("email"), String.valueOf(obj.get("password")), getGender(obj.get("gender")),
                                        obj.get("dob"), obj.get("cityName"), obj.get("mobileNo"), obj.get("profession")));
                            }
                            getData(mainDataList);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private List<FriendsListAndRequestModel> convertList(List<UserAccountModel> inputList) {
        List<FriendsListAndRequestModel> outputList = new ArrayList<>();
        for (UserAccountModel obj : inputList) {
            outputList.add(new FriendsListAndRequestModel(1, obj.getFirstName() + obj.getLastName(), obj.getMobileNo(), obj.getEmail()));
        }
        return outputList;
    }

    private void getData(List<UserAccountModel> mainDataList) {
        dataList = convertList(mainDataList);
        setData();
    }

    public void setData() {
        FriendsListAdapter adapter = new FriendsListAdapter(context, dataList);
        friendRequestLV.setAdapter(adapter);
        friendRequestLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final UserAccountModel dataModel = mainDataList.get(position);
                final String mail = dataModel.getEmail().replace(".", "_");
                UtilHelpers.showAlertDialog(getActivity(), dataModel.getFirstName(), "Accept Request?",
                        "Accept", "Reject", new TwoButtonAlertDialogCallback() {
                            @Override
                            public void onClick(CLICKTYPE type) {
                                if (type == CLICKTYPE.POSITIVE) {
                                    requestRef.child(mail).setValue(null);
                                    addFriendRef.child(mail).child(dataModel.getEmail().replace(".","_")).setValue("true");
                                } else {
                                    requestRef.child(mail).setValue(null);
                                }
                            }
                        });
                //FriendsDetailsFragment fragment = FriendsDetailsFragment.newInstance(dataModel);
                //getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_body, fragment).
                //addToBackStack(FRAGMENT_TAG).commit();
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.see_request_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem search = menu.findItem(R.id.action_search);
        search.setVisible(false);
        MenuItem setting = menu.findItem(R.id.action_settings);
        setting.setTitle("Reject!");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_friend:

                break;
            case R.id.action_settings:

                break;
        }
        return true;
    }

    private UserAccountModel.Gender getGender(String s) {
        if (s.equalsIgnoreCase("male"))
            return UserAccountModel.Gender.MALE;
        else if (s.equalsIgnoreCase("male"))
            return UserAccountModel.Gender.FEMALE;
        else
            return UserAccountModel.Gender.OTHER;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

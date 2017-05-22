package mehwish.ghazi.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mehwish.ghazi.R;
import mehwish.ghazi.adapter.FriendsListAdapter;
import mehwish.ghazi.model.FriendsListAndRequestModel;
import mehwish.ghazi.model.UserAccountModel;

/**
 * Created by Devprovider on 4/15/2017.
 */

public class TrackFriendListFragment extends Fragment {

    private DatabaseReference friendsListRef;
    private DatabaseReference dataListRef;
    private List<String> friendsList;
    private ProgressDialog progressDialog;
    private static final String FRAGMENT_TAG = "friendslistfragment";
    private List<FriendsListAndRequestModel> dataList = new ArrayList<>();
    private List<UserAccountModel> mainDataList = new ArrayList<>();
    private Context context;
    private String loggedInUserName;
    private ListView friendsListLV;

    public TrackFriendListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //showInstructions();
        return inflater.inflate(R.layout.fragment_track_friend_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        loggedInUserName = sp.getString("fbLoggedInUser", "");
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Fetching Friend's List");
        progressDialog.setMessage("Please wait...");
        progressDialog.setIcon(R.mipmap.fetching_icon);
        progressDialog.show();
        friendsListLV = (ListView) view.findViewById(R.id.track_friend_lv);
        friendsListRef = FirebaseDatabase.getInstance().getReferenceFromUrl
                ("https://friendsband-a3dc9.firebaseio.com/root/friendsRecord/" + loggedInUserName);

        dataListRef = FirebaseDatabase.getInstance().getReferenceFromUrl
                ("https://friendsband-a3dc9.firebaseio.com/root/userData/");
        getFriendNames();
//        getTempData();
//        setData();
    }

    private void getFriendNames() {

        friendsListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                friendsList = new ArrayList<String>();
                HashMap<String, String> hm = (HashMap<String, String>) dataSnapshot.getValue();
                friendsList = new ArrayList<String>(hm.keySet());
                dataListRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String, HashMap<String, String>> data = (HashMap<String, HashMap<String, String>>)
                                dataSnapshot.getValue();
                        for (HashMap.Entry<String, HashMap<String, String>> entry : data.entrySet()) {
                            String check = entry.getKey();
                            HashMap<String, String> obj = entry.getValue();
                            if(friendsList.contains(check)){
                                mainDataList.add(new UserAccountModel(obj.get("firstName"), obj.get("lastName"),
                                        obj.get("email"), obj.get("password"), getGender(obj.get("gender")),
                                        obj.get("dob"),obj.get("cityName"), obj.get("mobileNo"), obj.get("profession")));
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

    private void getData(List<UserAccountModel> mainDataList) {
        dataList = convertList(mainDataList);
        setData();
    }

    private List<FriendsListAndRequestModel> convertList(List<UserAccountModel> inputList){
        List<FriendsListAndRequestModel> outputList = new ArrayList<>();
        for(UserAccountModel obj : inputList){
            outputList.add(new FriendsListAndRequestModel(1, obj.getFirstName()+obj.getLastName(), obj.getMobileNo()));
        }
        return outputList;
    }

    public void showInstructions() {
        final AlertDialog swipeLeftDialog = new AlertDialog.Builder(getActivity(), R.style.swipe_left_instruction_dialog).
                create();
        swipeLeftDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLeftDialog.dismiss();
                final AlertDialog swipeRightDialog = new AlertDialog.Builder(getActivity(), R.style.swipe_right_instruction_dialog)
                        .create();
                swipeRightDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRightDialog.dismiss();
                    }
                }, 3000);
            }
        }, 3000);
    }


    public void setData() {
        FriendsListAdapter adapter = new FriendsListAdapter(context, dataList);
        friendsListLV.setAdapter(adapter);
        progressDialog.dismiss();
        friendsListLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = mainDataList.get(position).getEmail();
                ShowFriendsLocationFragment fragment = ShowFriendsLocationFragment.newInstance(name);
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_body, fragment).
                        addToBackStack(FRAGMENT_TAG).commit();
            }
        });

    }


    private UserAccountModel.Gender getGender(String s){
        if (s.equalsIgnoreCase("male"))
            return UserAccountModel.Gender.MALE;
        else if (s.equalsIgnoreCase("male"))
            return UserAccountModel.Gender.FEMALE;
        else
            return UserAccountModel.Gender.OTHER;
    }

}

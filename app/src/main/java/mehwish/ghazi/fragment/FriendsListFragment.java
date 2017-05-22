package mehwish.ghazi.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Handler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mehwish.ghazi.R;
import mehwish.ghazi.adapter.FriendsListAdapter;
import mehwish.ghazi.model.FriendsListAndRequestModel;
import mehwish.ghazi.model.UserAccountModel;

/**
 * Created by Devprovider on 3/7/2017.
 */

public class FriendsListFragment extends Fragment implements View.OnClickListener {

    private static final String FRAGMENT_TAG = "friendslistfragment";
    private List<FriendsListAndRequestModel> dataList = new ArrayList<>();
    private List<UserAccountModel> mainDataList = new ArrayList<>();
    private ListView friendsListLV;
    private Context context;
    private AlertDialog unfriendDialog;
    private static int toDelete;
    private Animation animRightSwipe;
    private Animation animLeftSwipe;
    private DatabaseReference friendsListRef;
    private DatabaseReference dataListRef;
    private List<String> friendsList;
    private ProgressDialog progressDialog;
    private String loggedInUserName;


    public FriendsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //showInstructions();
        return inflater.inflate(R.layout.fragment_friends_list, container, false);
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
        friendsListLV = (ListView) view.findViewById(R.id.friends_list_LV);
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
        animRightSwipe = AnimationUtils.loadAnimation(getActivity(), R.anim.trans_right_out);
        animRightSwipe.setDuration(500);
        animLeftSwipe = AnimationUtils.loadAnimation(getActivity(), R.anim.trans_left_out);
        FriendsListAdapter adapter = new FriendsListAdapter(context, dataList);
        friendsListLV.setAdapter(adapter);
        progressDialog.dismiss();
        friendsListLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserAccountModel dataModel = mainDataList.get(position);
                FriendsDetailsFragment fragment = FriendsDetailsFragment.newInstance(dataModel);
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_body, fragment).
                        addToBackStack(FRAGMENT_TAG).commit();
            }
        });
        friendsListLV.setOnTouchListener(new SwipeListener(getActivity(), friendsListLV));

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void showDeleteFriendDialog() {
        unfriendDialog = new AlertDialog.Builder(context).setTitle("Unfriend " + dataList.get(toDelete).getFriendName() + "?").
                setView(R.layout.unfriend_dialog_layout).create();
        unfriendDialog.setCancelable(true);
        unfriendDialog.setCanceledOnTouchOutside(true);
        unfriendDialog.show();
        TextView name = (TextView) unfriendDialog.findViewById(R.id.unfriend_name_tv);
        Button cancel = (Button) unfriendDialog.findViewById(R.id.cancel_unfriend);
        Button unfriend = (Button) unfriendDialog.findViewById(R.id.do_unfriend);
        name.setText(dataList.get(toDelete).getFriendName());
        cancel.setOnClickListener(this);
        unfriend.setOnClickListener(this);
    }

    public void getTempData() {
        for (int i = 0; i < 12; i++) {
            dataList.add(new FriendsListAndRequestModel(0, "Mehwish Ghazi" + i, "+92312345678" + i));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_unfriend:
                unfriendDialog.dismiss();
                break;
            case R.id.do_unfriend:
                dataList.remove(toDelete);
                setData();
                unfriendDialog.dismiss();
        }
    }

    public class SwipeListener implements View.OnTouchListener {

        private ListView list;
        private Context context;
        private GestureDetector gestureDetector;

        public SwipeListener(Context ctx, ListView list) {
            gestureDetector = new GestureDetector(ctx, new GestureListener());
            context = ctx;
            this.list = list;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        public void onSwipeRight(int pos) {
            int i = friendsListLV.getFirstVisiblePosition();
            View view = friendsListLV.getChildAt(pos - i);
            if (view != null) {
                view.startAnimation(animRightSwipe);
                Toast.makeText(context, "Message Friend (TODO)!", Toast.LENGTH_LONG).show();
            }
        }

        public void onSwipeLeft(int pos) {
            int i = friendsListLV.getFirstVisiblePosition();
            View view = friendsListLV.getChildAt(pos - i);
            if (view != null) {
                view.startAnimation(animLeftSwipe);
                toDelete = pos;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showDeleteFriendDialog();
                    }
                }, 300);
            }
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 3;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            private int getPostion(MotionEvent e1) {
                return list.pointToPosition((int) e1.getX(), (int) e1.getY());
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2,
                                   float velocityX, float velocityY) {
                float distanceX = e2.getX() - e1.getX();
                float distanceY = e2.getY() - e1.getY();
                if (Math.abs(distanceX) > Math.abs(distanceY)
                        && Math.abs(distanceX) > SWIPE_THRESHOLD
                        && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (distanceX > 0)
                        onSwipeRight(getPostion(e1));
                    else
                        onSwipeLeft(getPostion(e1));
                    return true;
                }
                return false;
            }

        }
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

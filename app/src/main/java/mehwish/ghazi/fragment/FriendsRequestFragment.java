package mehwish.ghazi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import mehwish.ghazi.R;
import mehwish.ghazi.adapter.FriendRequestsAdapter;
import mehwish.ghazi.adapter.FriendsListAdapter;
import mehwish.ghazi.model.FriendsListAndRequestModel;

/**
 * Created by Devprovider on 3/19/2017.
 */

public class FriendsRequestFragment extends Fragment {

    private Context context;
    private ListView friendRequestLV;
    private List<FriendsListAndRequestModel> dataList;

    public FriendsRequestFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend_requests,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        dataList = new ArrayList<>();
        getTempData();
        friendRequestLV = (ListView) view.findViewById(R.id.friend_requests_lv);
        FriendRequestsAdapter adapter = new FriendRequestsAdapter(context,dataList);
        friendRequestLV.setAdapter(adapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void getTempData(){
        for (int i=0; i<12; i++){
            dataList.add(new FriendsListAndRequestModel(0,"Misbah Naseer"+i,"+92312345678"+i));
        }
    }
}

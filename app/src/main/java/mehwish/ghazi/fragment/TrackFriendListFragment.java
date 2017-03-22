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
import mehwish.ghazi.adapter.FriendsListAdapter;
import mehwish.ghazi.adapter.TrackFriendListAdapter;
import mehwish.ghazi.model.FriendsListAndRequestModel;

/**
 * Created by Devprovider on 3/7/2017.
 */

public class TrackFriendListFragment extends Fragment{

    private List<FriendsListAndRequestModel> dataList = new ArrayList<>();
    private ListView friendsListLV;
    private Context context;

    public TrackFriendListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_track_friend_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        getTempData();
        friendsListLV = (ListView) view.findViewById(R.id.track_friend_lv);
        TrackFriendListAdapter adapter = new TrackFriendListAdapter(context,dataList);
        friendsListLV.setAdapter(adapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void getTempData(){
        for (int i=0; i<12; i++){
            dataList.add(new FriendsListAndRequestModel(0,"Mehwish Ghazi"+i,"+92312345678"+i));
        }
    }
}

package mehwish.ghazi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import mehwish.ghazi.R;

/**
 * Created by Devprovider on 3/7/2017.
 */

public class FriendsListFragment extends Fragment{

    public FriendsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends_list, container, false);


        // Inflate the layout for this fragment
        return rootView;
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }

}

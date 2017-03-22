package mehwish.ghazi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mehwish.ghazi.R;

/**
 * Created by Devprovider on 3/19/2017.
 */

public class FriendsDetailsFragment extends Fragment {

    private Context context;
    private static final String DETAILS_KEY = "contact_key";

    public static FriendsDetailsFragment newInstance(String contact) {
        Bundle args = new Bundle();
        args.putString(DETAILS_KEY,contact);
        FriendsDetailsFragment fragment = new FriendsDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friends_details,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String s = getArguments().getString(DETAILS_KEY);
    }
}

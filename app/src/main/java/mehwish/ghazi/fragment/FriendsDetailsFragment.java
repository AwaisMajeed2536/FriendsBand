package mehwish.ghazi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import mehwish.ghazi.R;
import mehwish.ghazi.helper.Constants;
import mehwish.ghazi.model.UserAccountModel;

/**
 * Created by Devprovider on 3/19/2017.
 */

public class FriendsDetailsFragment extends Fragment {

    protected View rootView;
    protected ImageView detailsFriendIV;
    protected TextView friendName;
    protected TextView friendEmail;
    protected TextView friendMobile;
    protected TextView friendHometown;
    protected TextView friendCurrentLocation;

    private String name, email, contact, homeTown, currentLocation;
    private Context context;
    private static final String DETAILS_KEY = "contact_key";

    public static FriendsDetailsFragment newInstance(UserAccountModel model) {

        Bundle args = new Bundle();
        args.putParcelable(Constants.USER_MODEL_KEY, model);
        FriendsDetailsFragment fragment = new FriendsDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_friends_details, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFragmentData();
        setFragmentData();
    }

    private void setFragmentData() {
        friendName.setText(name);
        friendEmail.setText(email);
        friendMobile.setText(contact);
        friendHometown.setText(homeTown);
        friendCurrentLocation.setText(currentLocation);
    }

    private void initView(View rootView) {
        detailsFriendIV = (ImageView) rootView.findViewById(R.id.details_friend_IV);
        friendName = (TextView) rootView.findViewById(R.id.details_friend_name);
        friendEmail = (TextView) rootView.findViewById(R.id.details_friend_email);
        friendMobile = (TextView) rootView.findViewById(R.id.details_friend_mobile);
        friendHometown = (TextView) rootView.findViewById(R.id.details_friend_hometown);
        friendCurrentLocation = (TextView) rootView.findViewById(R.id.details_friend_current_location);
    }

    private void getFragmentData(){
        UserAccountModel model = getArguments().getParcelable(Constants.USER_MODEL_KEY);
        name = model.getFirstName() + " " + model.getLastName();
        email = model.getEmail();
        contact = model.getMobileNo();
        homeTown = model.getCityName();
    }
}

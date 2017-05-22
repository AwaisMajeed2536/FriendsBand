package mehwish.ghazi.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import mehwish.ghazi.R;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Devprovider on 4/15/2017.
 */

public class ShowFriendsLocationFragment extends Fragment {


    private LocationManager locationManager;
    private GoogleMap googleMap;
    private LatLng myPosition;
    private Double lat, longt;
    private Marker currentLocationMarker;
    private DatabaseReference locationRef;
    private String friendName;
    private HashMap<String, Double> locationData;
    private ProgressDialog waitDialog;

    public static ShowFriendsLocationFragment newInstance(String friendName) {
        Bundle args = new Bundle();
        args.putString("FRIEND_NAME", friendName);
        ShowFriendsLocationFragment fragment = new ShowFriendsLocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ShowFriendsLocationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        waitDialog = new ProgressDialog(getActivity());
        waitDialog.setTitle("Finding Location");
        waitDialog.setMessage("Please Wait...");
        waitDialog.show();
        friendName = getArguments().getString("FRIEND_NAME");
        friendName = friendName.replace(".", "_");
        locationRef = FirebaseDatabase.getInstance().
                getReferenceFromUrl("https://friendsband-a3dc9.firebaseio.com/root/userLocations/" + friendName);
        locationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                locationData = (HashMap<String, Double>) dataSnapshot.getValue();
                lat = locationData.get("lat");
                longt = locationData.get("long");
                SupportMapFragment fm = (SupportMapFragment) getChildFragmentManager()
                        .findFragmentById(R.id.map);
                fm.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap map) {

                        if (ActivityCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            return;
                        }
                        waitDialog.dismiss();
                        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                        googleMap = map;
                        googleMap.setMyLocationEnabled(true);
                        if (lat >= 1 && longt >= 1 /*!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(longt)*/) {
                            Double latitude = lat;
                            Double longitude = longt;
                            Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());
                            myPosition = new LatLng(latitude, longitude);
                            googleMap.addMarker(new MarkerOptions().position(myPosition).title("My Current Location"));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 11));
                        } else {
                            Toast.makeText(getActivity(), "Couldn't get location!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}

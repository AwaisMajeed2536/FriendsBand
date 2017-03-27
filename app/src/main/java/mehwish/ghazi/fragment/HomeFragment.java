package mehwish.ghazi.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import mehwish.ghazi.R;

import static android.content.Context.LOCATION_SERVICE;


public class HomeFragment extends Fragment {

    private LocationManager locationManager;
    private GoogleMap googleMap;
    private LatLng myPosition;
    private String cityName;
    private Marker currentLocationMarker;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                Location currentLocation = getLastKnownLocation();
                googleMap = map;
                googleMap.setMyLocationEnabled(true);

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    if (currentLocation == null)
                        currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if(currentLocation!=null) {
                        Double latitude = currentLocation.getLatitude();
                        Double longitude = currentLocation.getLongitude();
                        cityName = null;
                        Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());
                        List<Address> addresses;
                        try {
                            addresses = gcd.getFromLocation(currentLocation.getLatitude(),
                                    currentLocation.getLongitude(), 1);
                            if (addresses.size() > 0) {
                                cityName = addresses.get(0).getLocality();
                                Toast.makeText(getActivity(), cityName, Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        myPosition = new LatLng(latitude, longitude);
                        googleMap.addMarker(new MarkerOptions().position(myPosition).title("My Current Location"));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 11));
                    } else{
                        Toast.makeText(getActivity(), "Couldn't get location!", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(getActivity(), "Please enable GPS!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Location getLastKnownLocation() {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.
                    checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

            }

            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

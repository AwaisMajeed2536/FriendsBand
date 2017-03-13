package mehwish.ghazi.fragment;

import android.Manifest;
import android.content.Context;
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
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import mehwish.ghazi.R;



public class HomeFragment extends Fragment {

    private LocationManager locationManager;
    private LocationListener locationListerner;
    private GoogleMap googleMap;
    private LatLng myPosition;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment fm = (SupportMapFragment) getActivity().getSupportFragmentManager().
                findFragmentById(R.id.map);
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
                googleMap = map;
                googleMap.setMyLocationEnabled(true);
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    Double latitude = currentLocation.getLatitude();
                    Double longitude = currentLocation.getLongitude();

                    myPosition = new LatLng(latitude,longitude);

                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(myPosition));
                }else{

                }

            }
        });
        /*if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationListerner = new MyLocationListener();
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20000, 10, locationListerner);
            } else {
                Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


                String longitude = "Longitude: " + currentLocation.getLongitude();
                String latitude = "Latitude: " + currentLocation.getLatitude();
                String cityName = null;
                Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());
                List<Address> addresses;
                try {
                    addresses = gcd.getFromLocation(currentLocation.getLatitude(),
                            currentLocation.getLongitude(), 1);
                    if (addresses.size() > 0) {
                        cityName = addresses.get(0).getLocality();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
                        + cityName;
                //editLocation.setText(s);
            }

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                Toast.makeText(getActivity(), "Can't ask for permission", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 9);
            }
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            //editLocation.setText("");
            Toast.makeText(getActivity(), "Location changed: Lat: " + loc.getLatitude() + " Lng: "
                    + loc.getLongitude(), Toast.LENGTH_SHORT).show();
            String longitude = "Longitude: " + loc.getLongitude();
            String latitude = "Latitude: " + loc.getLatitude();

            /*------- To get city name from coordinates -------- */
            String cityName = null;
            Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(),
                        loc.getLongitude(), 1);
                if (addresses.size() > 0) {
                    cityName = addresses.get(0).getLocality();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
                    + cityName;
            //editLocation.setText(s);
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

}
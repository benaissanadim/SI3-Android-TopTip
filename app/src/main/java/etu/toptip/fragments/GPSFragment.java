package etu.toptip.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.lights.LightsManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import etu.toptip.R;

public class GPSFragment extends Fragment {
    private IGPS  igps;
    private TextView placeNametext ;
    private Location currentLocation ;

    public GPSFragment(MapsFragment mapsFragment) {
        
    }

    public GPSFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater layoutInflater , ViewGroup viewGroup , Bundle savedInstanceState){
        View rootView = layoutInflater.inflate(R.layout.fragment_maps,viewGroup,false);
        placeNametext =  rootView.findViewById(R.id.placeName);
        final ImageView imageGPSGranted = rootView.findViewById(R.id.imageGPSGranted);
        final ImageView imageGPSactivated = rootView.findViewById(R.id.imageGPSActivated);
        boolean permissionGranted = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED;
        if(permissionGranted){
            imageGPSGranted.setImageResource(R.drawable.profil_background);
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    currentLocation=location;
                    igps.moveCamera();
                }
                @Override
                public void onProviderEnabled(String provider){
                    imageGPSactivated.setImageResource(R.drawable.unlocked);
                }
                @Override
                public void onProviderDisabled(String provider){
                    imageGPSactivated.setImageResource(R.drawable.unlocked);
                }
            };
            LocationManager locationManager = (LocationManager) (getActivity().getSystemService(Context.LOCATION_SERVICE));
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,1,locationListener);
            imageGPSactivated.setImageResource(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)?R.drawable.unlocked : R.drawable.locked);
        }else{
            imageGPSactivated.setImageResource(R.drawable.locked);
            imageGPSGranted.setImageResource(R.drawable.ic_profil);
        }
        return rootView ;
    }

    LatLng getPosition(){
        return  new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()) ;
    }

    String getPlaceName() throws IOException {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addressList =  geocoder.getFromLocation(currentLocation.getLatitude(),currentLocation.getLongitude(),1);
        return addressList.get(0).getLocality();
    }

    void setPLaceName(String pLaceName){
        placeNametext.setText(pLaceName);
    }
}

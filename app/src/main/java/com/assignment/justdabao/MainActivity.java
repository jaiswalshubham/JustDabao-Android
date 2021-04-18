package com.assignment.justdabao;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.assignment.justdabao.main.HomeFragment;
import com.assignment.justdabao.utils.GPSTracker;
import com.assignment.justdabao.utils.SuperActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends SuperActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gotoFragment(new HomeFragment(),null,null,this);
        fetchAddress();
    }

    private void fetchAddress() {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        GPSTracker gpsTracker = new GPSTracker();
        try {
            addresses = geocoder.getFromLocation(gpsTracker.getLatitude(), gpsTracker.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
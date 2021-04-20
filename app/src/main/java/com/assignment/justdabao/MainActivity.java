package com.assignment.justdabao;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.assignment.justdabao.main.CheckOutFragment;
import com.assignment.justdabao.main.HomeFragment;
import com.assignment.justdabao.main.models.AllItem;
import com.assignment.justdabao.utils.CommonUtil;
import com.assignment.justdabao.utils.Constants;
import com.assignment.justdabao.utils.GPSTracker;
import com.assignment.justdabao.utils.SuperActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends SuperActivity {

    TextView addressTxt,cartCount;
    String address;
    String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cartCount = findViewById(R.id.cart_count);
        updateCartCount();
        gotoFragment(new HomeFragment(),null,null,this);
        addressTxt = findViewById(R.id.address);
    if(checkPermission())
        fetchAddress();
    else requestPermission();

        ImageView cartImage = findViewById(R.id.cart_img);
        cartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cartCount.getText().toString().equalsIgnoreCase("0")){
                    gotoFragment(new CheckOutFragment(),null,"HomeFragment",MainActivity.this);
                }
            }
        });
    }
    private boolean checkPermission() {
        try {
            return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        } catch (Exception e) {
           e.printStackTrace();
        }
        return false;
    }

    private void requestPermission() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        fetchAddress();
    }

    private void fetchAddress() {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        GPSTracker gpsTracker = new GPSTracker(this);
        try {
            addresses = geocoder.getFromLocation(gpsTracker.getLatitude(), gpsTracker.getLongitude(), 1);
            // Here 1 represent max location result to returned, by documents it recommended 1 to 5
           if(addresses != null && addresses.size() >= 1){
                address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                city = addresses.get(0).getLocality();
               String state = addresses.get(0).getAdminArea();
               String country = addresses.get(0).getCountryName();
               String postalCode = addresses.get(0).getPostalCode();
               String knownName = addresses.get(0).getFeatureName();
               addressTxt.setText(address);
           }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updateCartCount(){
        String addedItem = CommonUtil.readPrefString(this, Constants.ADDED_ITEM);
        if(addedItem != null){
            AllItem allItem =  new Gson().fromJson(addedItem, new TypeToken<AllItem>() {
            }.getType());
            if(allItem != null){
                cartCount.setText(allItem.getAddItemList().size() + "");
            }
        }
    }
    public void setCartCountZero(){
        if(cartCount != null)
            cartCount.setText("0");
    }

    public String getAddress() {
        if(address == null)
            return "Bangalore";
        return address;
    }
}
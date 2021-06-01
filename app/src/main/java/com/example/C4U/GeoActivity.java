package com.example.C4U;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeoActivity extends AppCompatActivity {

    String location;

    public static Boolean isPushedToStack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GeoActivity.isPushedToStack = true;

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
            location = getLocation();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Intent data = new Intent();
            data.putExtra("loc", location);
            setResult(RESULT_OK, data);
            finish();
        }
    }

    public String getLocation() throws IOException {
        GpsTracker gpsTracker = new GpsTracker(GeoActivity.this);
        if (gpsTracker.canGetLocation()) {
            Geocoder gcd = new Geocoder(this, new Locale("US"));
            List<Address> addresses = gcd.getFromLocation(gpsTracker.getLatitude(), gpsTracker.getLongitude(), 1);
            if (addresses.size() > 0) {
                return addresses.get(0).getAddressLine(0);
            } else {
                return "Geolocation error, please try again";
            }
        } else {
            return "Geolocation error, please try again";
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        GeoActivity.isPushedToStack = false;
    }


}
package com.example.C4U;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Geolocalisation extends AppCompatActivity {
    FusedLocationProviderClient client;
    private Geocoder geocoder;
    public String locationName;
    public Intent data;
    //SupportMapFragment supportMapFragment;
    public static boolean isPushedToStack = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Geolocalisation.isPushedToStack = true;

        //setContentView(R.layout.activity_geolocalisation);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

        // initialize fused location
        client = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this, Locale.ENGLISH); // transforme les coordonées en adresse

        // check permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // if granted
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

    }

    private void getCurrentLocation() {
        // initialize task location

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {

            @Override
            public void onSuccess(Location location) {
                // when succes
                if (location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        if (addresses.size() > 0) {
                            Address address = addresses.get(0);
                            String locality = address.getLocality();
                            String country = address.getCountryName();


                            locationName = address.getAddressLine(0);

                            data = new Intent();
                            data.putExtra("loc", locationName);
                            setResult(RESULT_OK, data);
                            finish();
                        } else {
                            locationName = "Unknown address";
                            data = new Intent();
                            data.putExtra("loc", locationName);
                            setResult(RESULT_OK, data);
                            finish();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    /*
                           // create marker options
                           MarkerOptions options = new MarkerOptions().position(latLng).title("you are here");
                            //zoom
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                            //add Marker on map
                            googleMap.addMarker(options);
                             */
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // when permission granted
                getCurrentLocation();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Geolocalisation.isPushedToStack = false;
    }
}
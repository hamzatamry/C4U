package com.example.C4U;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.Bundle;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_geolocalisation);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

        // initialize fused location
        client = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this); // transforme les coordonées en adresse

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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {

            @Override
            public void onSuccess(Location location) {
                // when succes
                if( location != null){
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        if (addresses.size() > 0)
                        {
                            Address address = addresses.get(0);
                            String locality = address.getLocality();
                            String country = address.getCountryName();


                            locationName = address.getAddressLine(0);
                            Log.d("location",locationName);
                            Toast.makeText(Geolocalisation.this, locationName, Toast.LENGTH_SHORT).show();

                            data = new Intent();
                            data.putExtra("loc",locationName);
                            setResult(RESULT_OK, data);
                            finish();

                            //Toast.makeText(Geolocalisation.this, locationName, Toast.LENGTH_SHORT).show();

                        }
                        else{
                            locationName = "endroit inconnu";
                            data = new Intent();
                            data.putExtra("loc",locationName);
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
        if (requestCode == 44){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // when permission granted
                getCurrentLocation();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
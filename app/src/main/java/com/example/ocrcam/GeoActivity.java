package com.example.ocrcam;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.BoringLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeoActivity extends AppCompatActivity {
    private TextToSpeech textToSpeech;

    public static Boolean isPushedToStack = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        GeoActivity.isPushedToStack = true;

        super.onCreate(savedInstanceState);
        textToSpeech=new TextToSpeech(getApplicationContext(), status -> {
            if(status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.FRENCH);
            }
        });
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
            String location = getLocation();
            System.out.println(location);
            textToSpeech.speak(location, TextToSpeech.QUEUE_FLUSH, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finish();
        }
    }

    public String getLocation() throws IOException {
        GpsTracker gpsTracker = new GpsTracker(GeoActivity.this);
        if (gpsTracker.canGetLocation()) {
            Geocoder gcd = new Geocoder(this, new Locale("FR"));
            List<Address> addresses = gcd.getFromLocation(gpsTracker.getLatitude(), gpsTracker.getLongitude(), 1);
            if (addresses.size() > 0) {
                return addresses.get(0).getAddressLine(0);
            } else {
                return "Erreur de géolocalisation, veuillez réessayer ultérieurement";
            }
        } else {
            return "Erreur de géolocalisation, veuillez réessayer ultérieurement";
        }

    }

    @Override
    protected void onDestroy() {
        GeoActivity.isPushedToStack = false;
        super.onDestroy();
    }
}
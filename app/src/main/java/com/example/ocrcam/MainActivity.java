package com.example.ocrcam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private int layout;
    private SharedPreferences mPreferences;
    private final String sharedPrefFile = "com.c4u.appsharedprefs";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        startService(new Intent(getBaseContext(), SensorService.class));
        Intent intent1 = new Intent(MainActivity.this, MainActivity1.class); //buttons
        Intent intent2 = new Intent(MainActivity.this, MainActivity2.class); //swipes and stuff
        Intent intent3 = new Intent(MainActivity.this, MainActivity3.class); //voice
        Intent intent4 = new Intent(MainActivity.this, MainActivity4.class); //air gestures
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        layout= mPreferences.getInt("method", 1);
        super.onCreate(savedInstanceState);
        if (layout == 1)
            startActivity(intent1);
        else if (layout == 2)
            startActivity(intent2);
        else if (layout == 3)
            startActivity(intent3);
        else if (layout == 4)
            startActivity(intent4);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(getBaseContext(), SensorService.class));
    }
}
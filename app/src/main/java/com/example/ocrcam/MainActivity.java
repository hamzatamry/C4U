package com.example.ocrcam;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private int layout = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent1 = new Intent(MainActivity.this, MainActivity1.class); //buttons
        Intent intent2 = new Intent(MainActivity.this, MainActivity2.class); //swipes and stuff
        Intent intent3 = new Intent(MainActivity.this, MainActivity3.class); //voice
        Intent intent4 = new Intent(MainActivity.this, MainActivity2.class); //air gestures

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
}
package com.example.ocrcam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button ocrBt;
    private Button geoBt;
    private Button moneyBt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ocrBt = findViewById(R.id.ocrBt);
        geoBt = findViewById(R.id.geoBt);
        moneyBt = findViewById(R.id.moneyBt);

        ocrBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OcrActivity.class);
                startActivity(intent);
            }
        });

        moneyBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MoneyDetectActivity.class);
                startActivity(intent);
            }
        });

        geoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GeoActivity.class);
                startActivity(intent);
            }
        });
    }


}
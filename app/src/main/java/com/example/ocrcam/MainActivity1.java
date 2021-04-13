package com.example.ocrcam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity1 extends AppCompatActivity{
    private Button ocrBt;
    private Button geoBt;
    private Button moneyBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);


        moneyBt = findViewById(R.id.moneyBt);
        ocrBt = findViewById(R.id.ocrBt);
        geoBt = findViewById(R.id.geoBt);



        ocrBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity1.this, OcrActivity.class);
                startActivity(intent);
            }
        });


        moneyBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity1.this, MoneyDetectActivity.class);
                startActivity(intent);
            }
        });


        geoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity1.this, GeoActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.param:
                Intent intent = new Intent(MainActivity1.this, Parametre.class);
                startActivity(intent);
                return true;
            case R.id.help:
                Toast.makeText(this,"Help",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

}
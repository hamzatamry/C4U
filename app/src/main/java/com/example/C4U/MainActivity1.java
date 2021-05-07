package com.example.C4U;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity1 extends AppCompatActivity{
    private Button ocrBt;
    private Button geoBt;
    private Button moneyBt;

    private final static int REQUEST_CODE = 1;

    TextToSpeech textToSpeech;
    TextToSpeech.OnInitListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);


        moneyBt = findViewById(R.id.moneyBt);
        ocrBt = findViewById(R.id.ocrBt);
        geoBt = findViewById(R.id.geoBt);

        listener = status -> {
            if(status != TextToSpeech.ERROR){
                textToSpeech.setLanguage(Locale.FRENCH);
            }
        };

        textToSpeech = new TextToSpeech(getApplicationContext(), listener);
        textToSpeech.setLanguage(new Locale("fr", "FR"));

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
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        textToSpeech = new TextToSpeech(this, listener);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("loc")) {
                String localisation =  data.getExtras().getString("loc");
                Toast.makeText(this, localisation, Toast.LENGTH_SHORT).show();
                textToSpeech.speak(localisation,TextToSpeech.QUEUE_FLUSH,null);
            }
        }
    }

}
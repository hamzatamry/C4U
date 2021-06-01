package com.example.C4U;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ButtonsActivity extends AppCompatActivity {
    private Button ocrBt;
    private Button geoBt;
    private Button moneyBt;
    private Button colorBt;

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
        colorBt = findViewById(R.id.colorBt);

        listener = status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        };

        textToSpeech = new TextToSpeech(getApplicationContext(), listener);
        textToSpeech.setLanguage(Locale.ENGLISH);

        ocrBt.setOnClickListener(v -> {
            Intent intent = new Intent(ButtonsActivity.this, OcrActivity.class);
            startActivity(intent);
        });
        ocrBt.setOnLongClickListener(v -> {
            textToSpeech.speak(ocrBt.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            return true;
        });

        moneyBt.setOnClickListener(v -> {
            Intent intent = new Intent(ButtonsActivity.this, MoneyDetectActivity.class);
            startActivity(intent);
        });

        moneyBt.setOnLongClickListener(v -> {
            textToSpeech.speak(moneyBt.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            return true;
        });

        colorBt.setOnClickListener(v -> {
            Intent intent = new Intent(ButtonsActivity.this, ColorDetectActivity.class);
            startActivity(intent);
        });
        colorBt.setOnLongClickListener(v -> {
            textToSpeech.speak(colorBt.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            return true;
        });

        geoBt.setOnClickListener(v -> {
            Intent intent = new Intent(ButtonsActivity.this, GeoActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });
        geoBt.setOnLongClickListener(v -> {
            textToSpeech.speak(geoBt.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            return true;
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
                Intent intent = new Intent(ButtonsActivity.this, Parametre.class);
                startActivity(intent);
                return true;
            case R.id.help:
                textToSpeech.setLanguage(Locale.ENGLISH);
                String help = "Press a button to trigger the action, long press for description";
                textToSpeech.speak(help, TextToSpeech.QUEUE_FLUSH, null);
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
                String localisation = data.getExtras().getString("loc");
                textToSpeech.speak(localisation, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

}
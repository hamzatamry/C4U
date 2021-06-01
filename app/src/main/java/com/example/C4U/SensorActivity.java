package com.example.C4U;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Locale;

public class SensorActivity extends AppCompatActivity
{
    private final static int REQUEST_CODE = 1;
    TextToSpeech textToSpeech;
    TextToSpeech.OnInitListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        listener = status -> {
            if (status != TextToSpeech.ERROR)
            {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        };
        textToSpeech = new TextToSpeech(getApplicationContext(), listener);
        textToSpeech.setLanguage(Locale.ENGLISH);
        startService(new Intent(getBaseContext(), SensorService.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.param:
                Intent intent = new Intent(SensorActivity.this, Parametre.class);
                startActivity(intent);
                return true;
            case R.id.help:
                textToSpeech.setLanguage(Locale.ENGLISH);
                String help="Shake for geolocation, portrait for OCR and landscape for money detection";
                textToSpeech.speak(help, TextToSpeech.QUEUE_FLUSH, null);
                return true;
            default:
                return false;
        }
    }
}
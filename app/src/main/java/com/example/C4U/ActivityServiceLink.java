package com.example.C4U;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

public class ActivityServiceLink extends AppCompatActivity
{
    TextToSpeech textToSpeech;
    TextToSpeech.OnInitListener listener;
    private final static int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        listener = status -> {
            if (status != TextToSpeech.ERROR)
            {
                textToSpeech.setLanguage(Locale.US);
            }
        };
        textToSpeech = new TextToSpeech(getApplicationContext(), listener);
        textToSpeech.setLanguage(new Locale("en", "US"));
        this.geo();
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        textToSpeech = new TextToSpeech(this, listener);
    }

    public void geo()
    {
        if (!OcrActivity.isPushedToStack && !MoneyDetectActivity.isPushedToStack && !GeoActivity.isPushedToStack && !ColorDetectActivity.isPushedToStack)
        {
            Intent intent = new Intent(ActivityServiceLink.this, GeoActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE)
        {
            if (data.hasExtra("loc"))
            {
                String localisation =  data.getExtras().getString("loc");
                Toast.makeText(ActivityServiceLink.this, localisation, Toast.LENGTH_LONG).show();
                try
                {
                    Thread.sleep(1000);
                    textToSpeech.setSpeechRate(0.8f);
                    textToSpeech.speak(localisation, TextToSpeech.QUEUE_FLUSH,null);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

            }
        }
        finish();
    }
}
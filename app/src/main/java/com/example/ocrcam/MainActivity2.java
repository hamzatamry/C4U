package com.example.ocrcam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;

import java.util.Locale;

public class MainActivity2 extends AppCompatActivity{
    GestureDetectorCompat gesturesDetector;

    private final static int REQUEST_CODE = 1;
    TextToSpeech textToSpeech;
    TextToSpeech.OnInitListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        gesturesDetector = new GestureDetectorCompat(this,new GestureListener());

        listener = status -> {
            if(status != TextToSpeech.ERROR){
                textToSpeech.setLanguage(Locale.FRENCH);
            }
        };

        textToSpeech = new TextToSpeech(getApplicationContext(), listener);
        textToSpeech.setLanguage(new Locale("fr", "FR"));

        /*
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.gestures);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int event_type = event.getActionMasked();

                switch(event_type){
                    case MotionEvent.ACTION_POINTER_UP:
                        int pointers = event.getPointerCount() ;
                        switch(pointers) {
                            case 2:
                                Log.d("gestures", "2 taps");
                                ocr();
                                break;
                            case 3:
                                Log.d("gestures", "3 taps");
                                moneyDetect();
                                break;
                            case 4:
                                Log.d("gestures", "4 taps");
                                geo();
                                break;
                        }
                        break;
                }
                return false;
            }
        });

         */
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
                Intent intent = new Intent(MainActivity2.this, Parametre.class);
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
    protected void onResume() {
        super.onResume();
        textToSpeech = new TextToSpeech(this, listener);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            textToSpeech.speak("geolocation", TextToSpeech.QUEUE_FLUSH, null);
            Toast.makeText(MainActivity2.this, "Geolocation", Toast.LENGTH_SHORT).show();
            geo();
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            textToSpeech.speak("OCR", TextToSpeech.QUEUE_FLUSH, null);
            Toast.makeText(MainActivity2.this, "OCR", Toast.LENGTH_SHORT).show();
            ocr();
            return super.onDoubleTap(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            textToSpeech.speak("Money detection", TextToSpeech.QUEUE_FLUSH, null);
            Toast.makeText(MainActivity2.this, "Money detection", Toast.LENGTH_SHORT).show();
            moneyDetect();
            super.onLongPress(e);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gesturesDetector.onTouchEvent(event);
        int event_type = event.getActionMasked();
        return super.onTouchEvent(event);
    }

    public void ocr()
    {
        Intent intent = new Intent(MainActivity2.this, OcrActivity.class);
        startActivity(intent);
    }

    public void moneyDetect()
    {
        Intent intent = new Intent(MainActivity2.this, MoneyDetectActivity.class);
        startActivity(intent);
    }

    public void geo()
    {
        Intent intent = new Intent(MainActivity2.this, GeoActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
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
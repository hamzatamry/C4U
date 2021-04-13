package com.example.c4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.media.midi.MidiDeviceService;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    GestureDetectorCompat gesturesDetector;
    TextToSpeech tts;
    EditText edit;
    Button button, mapsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gesturesDetector = new GestureDetectorCompat(this,new GestureListener());
        edit = (EditText)findViewById(R.id.input);
        button = (Button) findViewById(R.id.button);
        mapsButton = (Button) findViewById(R.id.button2);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = edit.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onPause(){
        if(tts !=null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Toast.makeText(MainActivity.this, "Fling Confirmed", Toast.LENGTH_SHORT).show();
            tts.speak("Fling Confirmed", TextToSpeech.QUEUE_FLUSH, null);
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Toast.makeText(MainActivity.this, "Double Tap Confirmed", Toast.LENGTH_SHORT).show();
            tts.speak("Double Tap Confirmed", TextToSpeech.QUEUE_FLUSH, null);
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Toast.makeText(MainActivity.this, "Single Tap Confirmed", Toast.LENGTH_SHORT).show();
            tts.speak("Single Tap Confirmed", TextToSpeech.QUEUE_FLUSH, null);
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Toast.makeText(MainActivity.this, "Long press Confirmed", Toast.LENGTH_SHORT).show();
            tts.speak("Long press Confirmed", TextToSpeech.QUEUE_FLUSH, null);
            super.onLongPress(e);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gesturesDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
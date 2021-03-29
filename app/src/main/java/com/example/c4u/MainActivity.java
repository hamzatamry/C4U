package com.example.c4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.media.midi.MidiDeviceService;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    GestureDetectorCompat gesturesDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gesturesDetector = new GestureDetectorCompat(this,new GestureListener());

    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Toast.makeText(MainActivity.this, "Fling Confirmed", Toast.LENGTH_SHORT).show();
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Toast.makeText(MainActivity.this, "Double Tap Confirmed", Toast.LENGTH_SHORT).show();
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Toast.makeText(MainActivity.this, "Single Tap Confirmed", Toast.LENGTH_SHORT).show();
            return super.onSingleTapConfirmed(e);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gesturesDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
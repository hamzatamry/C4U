package com.example.C4U;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;


import androidx.annotation.Nullable;

import java.util.Locale;



public class SensorService extends Service implements SensorEventListener, TextToSpeech.OnInitListener
{
    private SensorManager sensorManager = null;
    private float acceleration = 0;
    private float currentAcceleration = 0;
    private float lastAcceleration = 0;
    private final int accelerationThreshold = 12;

    private float previousXOrientation = 0;
    private float previousYOrientation = 0;
    private float currentXOrientation = 0;
    private float currentYOrientation = 0;
    private boolean hasMovedOnX = true;
    private boolean hasMovedOnY = true;
    private final int minXOrientation = -100;
    private final int maxXOrientation = -70;
    private final int minYOrientation = 70;
    private final int maxYOrientation = 100;
    private final int variationThreshold = 8;

    private float previousLx = 0;
    private float currentLx = 0;
    private int lxVariationThreshold = 10;
    private boolean lxHasChanged = true;

    private TextToSpeech textToSpeech;

    @Override
    public void onCreate()
    {
        textToSpeech = new TextToSpeech(this, this);
        textToSpeech.setSpeechRate(0.8f);
        super.onCreate();
    }

    @Override
    public void onInit(int status)
    {
        if (status == TextToSpeech.SUCCESS)
        {
            int result = textToSpeech.setLanguage(Locale.US);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.acceleration = 10f;
        this.currentAcceleration = SensorManager.GRAVITY_EARTH;
        this.lastAcceleration = SensorManager.GRAVITY_EARTH;

        registerSensorEventListener(Sensor.TYPE_ACCELEROMETER);
        registerSensorEventListener(Sensor.TYPE_ORIENTATION);
        registerSensorEventListener(Sensor.TYPE_LIGHT);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public void onSensorChanged(SensorEvent event)
    {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                checkShakeMovement(event);
                break;
            case Sensor.TYPE_ORIENTATION:
                checkPhonePosition(event);
                break;
            case Sensor.TYPE_LIGHT:
                checkAbsenceOfLight(event);
            default:
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { }

    private void registerSensorEventListener(int sensorType)
    {
        Sensor sensor = this.sensorManager.getDefaultSensor(sensorType);
        this.sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void checkShakeMovement(SensorEvent event)
    {
        float xAcceleration = event.values[0];
        float yAcceleration = event.values[1];
        float zAcceleration = event.values[2];

        lastAcceleration = currentAcceleration;

        //Calculer la norme de vecteur acceleration
        currentAcceleration = (float) Math.sqrt((double) (xAcceleration * xAcceleration + yAcceleration * yAcceleration + zAcceleration * zAcceleration));

        float delta = currentAcceleration - lastAcceleration; //variation de l'acceleration

        acceleration = acceleration * 0.9f + delta;

        if (acceleration > accelerationThreshold)
        {
            System.out.println("Shake Movement detected");
            this.geo();
        }
    }

    public void checkPhonePosition(SensorEvent event)
    {
        this.previousXOrientation = this.currentXOrientation;
        this.previousYOrientation = this.currentYOrientation;

        this.currentXOrientation = event.values[1];
        this.currentYOrientation = event.values[2];

        if (hasMovedOnX)
        {
            if (minXOrientation <= currentXOrientation && currentXOrientation <= maxXOrientation)
            {
                this.ocr();
                hasMovedOnX = false;
            }
        }

        if (Math.abs(previousXOrientation - currentXOrientation) > variationThreshold)
        {
            hasMovedOnX = true;
        }

        if (hasMovedOnY)
        {
            if (minYOrientation <= currentYOrientation && currentYOrientation <= maxYOrientation)
            {
                this.moneyDetect();
                hasMovedOnY = false;
            }
        }

        if (Math.abs(previousYOrientation - currentYOrientation) > variationThreshold)
        {
            hasMovedOnY = true;
        }
    }

    public void checkAbsenceOfLight(SensorEvent event)
    {
        previousLx = currentLx;
        currentLx = event.values[0];

        System.out.println(currentLx);

        if (Math.abs(previousLx - currentLx) > lxVariationThreshold)
        {
            lxHasChanged = true;
        }

        if (lxHasChanged)
        {
            if (currentLx < 1)
            {
                this.colorDetect();
                lxHasChanged = false;
            }
        }
    }

    public void ocr()
    {
        if (!OcrActivity.isPushedToStack && !MoneyDetectActivity.isPushedToStack && !GeoActivity.isPushedToStack && !ColorDetectActivity.isPushedToStack) {
            Intent intent = new Intent(getApplicationContext(), OcrActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            textToSpeech.speak("Camera opened for Image to speech", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void moneyDetect()
    {
        if (!OcrActivity.isPushedToStack && !MoneyDetectActivity.isPushedToStack && !GeoActivity.isPushedToStack && !ColorDetectActivity.isPushedToStack) {
            Intent intent = new Intent(getApplicationContext(), MoneyDetectActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            textToSpeech.speak("Camera opened for Money Detection", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void geo()
    {
        if (!OcrActivity.isPushedToStack && !MoneyDetectActivity.isPushedToStack && !GeoActivity.isPushedToStack && !ColorDetectActivity.isPushedToStack) {
            Intent intent = new Intent(getApplicationContext(), ActivityServiceLink.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }


    public void colorDetect()
    {
        if (!OcrActivity.isPushedToStack && !MoneyDetectActivity.isPushedToStack
                && !GeoActivity.isPushedToStack && !ColorDetectActivity.isPushedToStack)
        {
            Intent intent = new Intent(getApplicationContext(), ColorDetectActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            textToSpeech.speak("Camera opened for Color Detection", TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
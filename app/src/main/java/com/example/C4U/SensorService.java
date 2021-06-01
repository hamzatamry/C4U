package com.example.C4U;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.widget.Toast;
import androidx.annotation.Nullable;


public class SensorService extends Service implements SensorEventListener
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


    public void onSensorChanged (SensorEvent event)
    {
        switch (event.sensor.getType())
        {
            case Sensor.TYPE_ACCELEROMETER:
                checkShakeMovement(event);
                break;
            case Sensor.TYPE_ORIENTATION:
                checkPhonePosition(event);
                break;
            //case Sensor.TYPE_LIGHT:
                //checkAbsenceOfLight(event);
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
        if (event.values[0] <= 1)
        {

            Toast mToastToShow = Toast.makeText(this, "No Light Detected", Toast.LENGTH_SHORT);

            CountDownTimer toastCountDown;
            toastCountDown = new CountDownTimer(1, 1) {
                public void onTick(long millisUntilFinished)
                {
                    mToastToShow.show();
                }
                public void onFinish() {
                    mToastToShow.cancel();
                }
            };

            mToastToShow.show();
            toastCountDown.start();
        }
    }

    public void ocr()
    {
        if (!OcrActivity.isPushedToStack && !MoneyDetectActivity.isPushedToStack && !GeoActivity.isPushedToStack)
        {
            Intent intent = new Intent(getApplicationContext(), OcrActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    public void moneyDetect()
    {
        if (!OcrActivity.isPushedToStack && !MoneyDetectActivity.isPushedToStack && !GeoActivity.isPushedToStack)
        {
            Intent intent = new Intent(getApplicationContext(), MoneyDetectActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    public void geo()
    {
        if (!OcrActivity.isPushedToStack && !MoneyDetectActivity.isPushedToStack && !GeoActivity.isPushedToStack)
        {
            Intent intent = new Intent(getApplicationContext(), GeoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //startActivity(intent);
        }
    }
}
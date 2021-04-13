package com.example.c4u;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Locale;

public class SensorService extends Service implements SensorEventListener
{
    private SensorManager sensorManager = null;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private TextToSpeech ttobj = null;

    public SensorService()
    {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.mAccel = 10f;
        this.mAccelCurrent = SensorManager.GRAVITY_EARTH;
        this.mAccelLast = SensorManager.GRAVITY_EARTH;

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

    @Override
    public void onDestroy()
    {
        super.onDestroy();
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
            case Sensor.TYPE_LIGHT:
                checkAbsenceOfLight(event);
            default:
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void registerSensorEventListener(int sensorType)
    {
        Sensor sensor = this.sensorManager.getDefaultSensor(sensorType);
        this.sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void checkShakeMovement(SensorEvent event)
    {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
        float delta = mAccelCurrent - mAccelLast;
        mAccel = mAccel * 0.9f + delta;

        if (mAccel > 12)
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Shake movement detected", Toast.LENGTH_SHORT);
            CountDownTimer toastCountDown = new CountDownTimer(1000, 1000) {
                public void onTick(long millisUntilFinished)
                {
                    toast.show();
                }
                public void onFinish()
                {
                    toast.cancel();
                }
            };
            toast.show();
            toastCountDown.start();

        }
    }

    public void checkPhonePosition(SensorEvent event)
    {
        float xOrientation = event.values[1];
        float yOrientation = event.values[2];
        String position = "";

        if (-90 <= xOrientation && xOrientation <= -60)
        {
            position = "Portrait position";
        }

        if ((70 <= yOrientation && yOrientation <= 100) || (-100 <= yOrientation && yOrientation <= -70))
        {
            position = "Landscape position";
        }

        if ((-20 <= xOrientation && xOrientation <= 10) && (-10 <= yOrientation && yOrientation <= 10))
        {
            System.out.println("xOrientation " + String.valueOf(xOrientation));
            System.out.println("yOrientation " + String.valueOf(yOrientation));
            position = "Vertical position";
        }

        if (position != "")
        {
            Toast toast = Toast.makeText(getApplicationContext(), position, Toast.LENGTH_SHORT);
            CountDownTimer toastCountDown;
            toastCountDown = new CountDownTimer(1, 1) {
                public void onTick(long millisUntilFinished)
                {
                    toast.show();
                }
                public void onFinish()
                {
                    toast.cancel();
                }
            };
            toast.show();
            toastCountDown.start();
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
}
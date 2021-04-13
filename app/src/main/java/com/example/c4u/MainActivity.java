package com.example.c4u;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.hardware.*;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SensorEventListener
{
    private SensorManager sensorManager = null;
    private TextView accelerationXTextView = null;
    private TextView accelerationYTextView = null;
    private TextView accelerationZTextView = null;
    private TextView orientationXTextView = null;
    private TextView orientationYTextView = null;
    private TextView orientationZTextView = null;
    private TextView lightTextView = null;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private TextToSpeech ttobj = null;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accelerationXTextView = findViewById(R.id.accelerationX);
        accelerationYTextView = findViewById(R.id.accelerationY);
        accelerationZTextView = findViewById(R.id.accelerationZ);
        orientationXTextView = findViewById(R.id.orientationX);
        orientationYTextView = findViewById(R.id.orientationY);
        orientationZTextView = findViewById(R.id.orientationZ);
        lightTextView = findViewById(R.id.light);
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        registerSensorEventListener(Sensor.TYPE_ACCELEROMETER);
        registerSensorEventListener(Sensor.TYPE_ORIENTATION);
        registerSensorEventListener(Sensor.TYPE_LIGHT);

        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;



    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    public void onAccuracyChanged (Sensor sensor, int accuracy)
    {
        System.out.println("onAccuracyChanged called");
        System.out.println(sensor.toString() + " " + accuracy);
    }

    public void onSensorChanged (SensorEvent event)
    {
        switch (event.sensor.getType())
        {
            case Sensor.TYPE_ACCELEROMETER:
                accelerationXTextView.setText("Acceleration x-axis: " + event.values[0]);
                accelerationYTextView.setText("Acceleration y-axis: " + event.values[1]);
                accelerationZTextView.setText("Acceleration z-axis: " + event.values[2]);

                //Detect shake
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                mAccelLast = mAccelCurrent;
                mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
                float delta = mAccelCurrent - mAccelLast;
                mAccel = mAccel * 0.9f + delta;
                if (mAccel > 12) {

                    ttobj = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR){
                                ttobj.setLanguage(Locale.UK);
                            }

                            ttobj.speak("Shake event detected", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    });


                    Toast.makeText(getApplicationContext(), "Shake event detected", Toast.LENGTH_SHORT).show();
                }
                break;
            case Sensor.TYPE_ORIENTATION:

                float xOrientation = event.values[1];
                float yOrientation = event.values[2];
                Log.i("xOrientation: ", String.valueOf(xOrientation));
                Log.i("yOrientation: ", String.valueOf(yOrientation));
                String text = "";

                if (-90 <= xOrientation && xOrientation <= -70)
                {
                    text = "Portrait position";
                }

                if (-20 <= xOrientation && xOrientation <= 1)
                {
                    text = "Vertical position";
                }

                if (70 <= yOrientation && yOrientation <= 90 || -90 <= yOrientation && yOrientation <= -70)
                {
                    text = "Landscape position";

                }

                orientationXTextView.setText("Orientation x-axis: " + event.values[1]);
                orientationYTextView.setText("Orientation y-axis: " + event.values[2]);
                orientationZTextView.setText("Orientation z-axis: " + event.values[0]);

                if (text != "")
                {
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);

                    CountDownTimer toastCountDown;
                    toastCountDown = new CountDownTimer(1, 1) {
                        public void onTick(long millisUntilFinished) {
                            toast.show();


                        }
                        public void onFinish() {
                            toast.cancel();
                        }
                    };
                    toast.show();
                    toastCountDown.start();
                }

                break;
            case Sensor.TYPE_LIGHT:
                if (event.values[0] <= 1)
                {
                    Toast mToastToShow = Toast.makeText(this, "No Light Detected", Toast.LENGTH_SHORT);

                    CountDownTimer toastCountDown;
                    toastCountDown = new CountDownTimer(1, 1) {
                        public void onTick(long millisUntilFinished) {
                            mToastToShow.show();


                        }
                        public void onFinish() {
                            mToastToShow.cancel();
                        }
                    };

                    // Show the toast and starts the countdown
                    mToastToShow.show();
                    toastCountDown.start();


                }
                lightTextView.setText("Illuminance: " + event.values[0]);



            default:
                break;
        }

    }

    private void registerSensorEventListener(int sensorType)
    {
        Sensor sensor = this.sensorManager.getDefaultSensor(sensorType);
        this.sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void checkSensorList()
    {
        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        Iterator<Sensor> listIterator = deviceSensors.iterator();

        while (listIterator.hasNext())
        {
            Sensor sensor = listIterator.next();
            System.out.println(sensor);
        }
    }

    private Boolean checkIfAccelerometerSensorExists()
    {
        return sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null;
    }

    private Boolean checkIfGravitySensorExists()
    {
        return sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null;
    }

    private Boolean checkIfGyroscopeSensorExists()
    {
        return sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null;
    }

    private Boolean checkIfLinearAccelerationSensorExists()
    {
        return sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null;
    }

    private Boolean checkIfOrientationSensorExists()
    {
        return sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null;
    }

    private Boolean checkIfRotationVectorSensorExists()
    {
        return sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null;
    }

    private Boolean checkIfGeomagneticRotationVectorSensorExists()
    {
        return sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) != null;
    }

    private Boolean isStreamingSensor(Sensor sensor)
    {
        return sensor.getMinDelay() != 0;
    }

    private void checkSensorStreamingList()
    {
        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        Iterator<Sensor> listIterator = deviceSensors.iterator();

        while (listIterator.hasNext())
        {
            Sensor sensor = listIterator.next();
            System.out.println(sensor.toString() + " " + isStreamingSensor(sensor).toString());
        }
    }
}
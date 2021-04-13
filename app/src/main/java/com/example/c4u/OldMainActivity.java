package com.example.c4u;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.hardware.*;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

public class OldMainActivity extends Activity implements SensorEventListener
{
    private SensorManager sensorManager = null;
    private TextView accelerationXTextView = null;
    private TextView accelerationYTextView = null;
    private TextView accelerationZTextView = null;
    private TextView orientationXTextView = null;
    private TextView orientationYTextView = null;
    private TextView orientationZTextView = null;
    private TextView lightTextView;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private TextToSpeech ttobj = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLayoutView();
        startService(new Intent(getBaseContext(), SensorService.class));
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
                checkShakeMovement(event);
                break;
            case Sensor.TYPE_ORIENTATION:
                orientationXTextView.setText("Orientation x-axis: " + event.values[1]);
                orientationYTextView.setText("Orientation y-axis: " + event.values[2]);
                orientationZTextView.setText("Orientation z-axis: " + event.values[0]);
                checkPhonePosition(event);
                break;
            case Sensor.TYPE_LIGHT:
                lightTextView.setText("Illuminance: " + event.values[0]);
                checkAbsenceOfLight(event);
            default:
                break;
        }

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
        else if (-20 <= xOrientation && xOrientation <= 1)
        {
            position = "Vertical position";
        }
        else if (70 <= yOrientation && yOrientation <= 100 || -100 <= yOrientation && yOrientation <= -70)
        {
            position = "Landscape position";
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

    private void setLayoutView()
    {
        accelerationXTextView = findViewById(R.id.accelerationX);
        accelerationYTextView = findViewById(R.id.accelerationY);
        accelerationZTextView = findViewById(R.id.accelerationZ);
        orientationXTextView = findViewById(R.id.orientationX);
        orientationYTextView = findViewById(R.id.orientationY);
        orientationZTextView = findViewById(R.id.orientationZ);
        lightTextView = findViewById(R.id.light);
    }
}


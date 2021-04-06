package com.example.c4u;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.hardware.*;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SensorEventListener
{
    private SensorManager sensorManager = null;
    TextView pitchTextView = null;
    TextView rollTextView = null;
    TextView azimuthTextView = null;

    TextView orientationXTextView;
    TextView orientationYTextView;
    TextView orientationZTextView;
    TextView Proximity;
    TextView LightTextView;

    TextView GeomagneticRotationX;
    TextView GeomagneticRotationY;
    TextView GeomagneticRotationZ;

    private void registerSensorEventListener(int sensorType)
    {
        Sensor sensor = this.sensorManager.getDefaultSensor(sensorType);
        this.sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pitchTextView = findViewById(R.id.Pitch);
        rollTextView = findViewById(R.id.Roll);
        azimuthTextView = findViewById(R.id.Azimuth);
        orientationXTextView = findViewById(R.id.OrientationX);
        orientationYTextView = findViewById(R.id.OrientationY);
        orientationZTextView = findViewById(R.id.OrientationZ);
        Proximity = findViewById(R.id.Proximity);
        LightTextView = findViewById(R.id.LightSensor);
        GeomagneticRotationX = findViewById(R.id.GeomagneticRotationX);
        GeomagneticRotationY = findViewById(R.id.GeomagneticRotationY);
        GeomagneticRotationZ = findViewById(R.id.GeomagneticRotationZ);

        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        registerSensorEventListener(Sensor.TYPE_ACCELEROMETER);
        registerSensorEventListener(Sensor.TYPE_ORIENTATION);
        registerSensorEventListener(Sensor.TYPE_PROXIMITY);
        registerSensorEventListener(Sensor.TYPE_LIGHT);
        registerSensorEventListener(Sensor.);
        registerSensorEventListener(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
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
                pitchTextView.setText("x    : " + event.values[0]);
                rollTextView.setText("y : " + event.values[1]);
                azimuthTextView.setText("z  : " + event.values[2]);
                break;
            case Sensor.TYPE_ORIENTATION:
                orientationXTextView.setText("z-axis (°)" + event.values[0]);
                orientationYTextView.setText("x-axis (°)" + event.values[1]);
                orientationZTextView.setText("y-axis (°)" + event.values[2]);
                break;
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                GeomagneticRotationX.setText("x * sin(θ/2): " + event.values[0]);
                GeomagneticRotationY.setText("y * sin(θ/2): " + event.values[1]);
                GeomagneticRotationZ.setText("z * sin(θ/2): " + event.values[2]);
                break;
            case Sensor.TYPE_PROXIMITY:
                Proximity.setText("cm: " + event.values[0]);
                break;
            case Sensor.TYPE_LIGHT:
                LightTextView.setText("lx: " + event.values[0]);
            default:
                break;
        }

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
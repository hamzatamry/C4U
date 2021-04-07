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
    private TextView accelerationXTextView = null;
    private TextView accelerationYTextView = null;
    private TextView accelerationZTextView = null;
    private TextView orientationXTextView = null;
    private TextView orientationYTextView = null;
    private TextView orientationZTextView = null;
    private TextView proximityTextView = null;
    private TextView lightTextView = null;
    private TextView geomagneticRotationXTextView = null;
    private TextView geomagneticRotationYTextView = null;
    private TextView geomagneticRotationZTextView = null;

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
        proximityTextView = findViewById(R.id.proximity);
        lightTextView = findViewById(R.id.light);
        geomagneticRotationXTextView = findViewById(R.id.geomagneticRotationX);
        geomagneticRotationYTextView = findViewById(R.id.geomagneticRotationY);
        geomagneticRotationZTextView = findViewById(R.id.geomagneticRotationZ);

        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        registerSensorEventListener(Sensor.TYPE_ACCELEROMETER);
        registerSensorEventListener(Sensor.TYPE_ORIENTATION);
        registerSensorEventListener(Sensor.TYPE_PROXIMITY);
        registerSensorEventListener(Sensor.TYPE_LIGHT);
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
                accelerationXTextView.setText("Acceleration x-axis: " + event.values[0]);
                accelerationYTextView.setText("Acceleration y-axis: " + event.values[1]);
                accelerationZTextView.setText("Acceleration z-axis: " + event.values[2]);
                break;
            case Sensor.TYPE_ORIENTATION:
                orientationXTextView.setText("Orientation x-axis: " + event.values[1]);
                orientationYTextView.setText("Orientation y-axis: " + event.values[2]);
                orientationZTextView.setText("Orientation z-axis: " + event.values[0]);
                break;
            case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                geomagneticRotationXTextView.setText("Geomagnetic Rotation x-axis: " + event.values[0]);
                geomagneticRotationYTextView.setText("Geomagnetic Rotation y-axis: " + event.values[1]);
                geomagneticRotationZTextView.setText("Geomagnetic Rotation z-axis: " + event.values[2]);
                break;
            case Sensor.TYPE_PROXIMITY:
                proximityTextView.setText("Proximity: " + event.values[0]);
                break;
            case Sensor.TYPE_LIGHT:
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
package com.example.c4u;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.hardware.*;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity
{
    private static SensorManager sensorManager = null;

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
        return sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED) != null;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        System.out.println("AccelerometerSensor " + checkIfAccelerometerSensorExists());
        System.out.println("GravitySensor " + checkIfGravitySensorExists());
        System.out.println("GyroscopeSensor " + checkIfGyroscopeSensorExists());
        System.out.println("LinearAccelerationSensor " + checkIfLinearAccelerationSensorExists());
        System.out.println("OrientationSensor " + checkIfOrientationSensorExists());
        System.out.println("RotationVectorSensor " + checkIfRotationVectorSensorExists());
    }
}
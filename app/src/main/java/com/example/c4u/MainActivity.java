package com.example.c4u;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.hardware.*;
import android.util.Log;

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

public class MainActivity extends AppCompatActivity
{
    private SensorManager sensorManager = null;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        Sensor accelerometerSensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        checkSensorStreamingList();

        /*


        checkSensorList();
        System.out.println("AccelerometerSensor " + checkIfAccelerometerSensorExists());
        System.out.println("GravitySensor " + checkIfGravitySensorExists());
        System.out.println("GyroscopeSensor " + checkIfGyroscopeSensorExists());
        System.out.println("LinearAccelerationSensor " + checkIfLinearAccelerationSensorExists());
        System.out.println("OrientationSensor " + checkIfOrientationSensorExists());
        System.out.println("RotationVectorSensor " + checkIfRotationVectorSensorExists());
        System.out.println("GeomagneticRotationVectorSensor "+ checkIfGeomagneticRotationVectorSensorExists());
        */



    }

}
package com.example.C4U;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import java.util.Iterator;
import java.util.List;

public class SensorChecker
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
}

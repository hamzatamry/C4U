package com.example.C4U;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import java.util.Iterator;
import java.util.List;

public class SensorChecker {
    private SensorManager sensorManager = null;
    private static SensorChecker sensorChecker = new SensorChecker();

    private SensorChecker() {
    }

    public static SensorChecker getSensorCheckerInstance() {
        return SensorChecker.sensorChecker;
    }

    public void checkSensorList() {
        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        Iterator<Sensor> listIterator = deviceSensors.iterator();

        while (listIterator.hasNext()) {
            Sensor sensor = listIterator.next();
            System.out.println(sensor);
        }
    }

    public Boolean checkIfAccelerometerSensorExists() {
        return sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null;
    }

    public Boolean checkIfGravitySensorExists() {
        return sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null;
    }

    public Boolean checkIfGyroscopeSensorExists() {
        return sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null;
    }

    public Boolean checkIfLinearAccelerationSensorExists() {
        return sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null;
    }

    public Boolean checkIfOrientationSensorExists() {
        return sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null;
    }

    public Boolean checkIfRotationVectorSensorExists() {
        return sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null;
    }

    public Boolean checkIfGeomagneticRotationVectorSensorExists() {
        return sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) != null;
    }

    public Boolean isStreamingSensor(Sensor sensor) {
        return sensor.getMinDelay() != 0;
    }

    public void checkSensorStreamingList() {
        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        Iterator<Sensor> listIterator = deviceSensors.iterator();

        while (listIterator.hasNext()) {
            Sensor sensor = listIterator.next();
            System.out.println(sensor.toString() + " " + isStreamingSensor(sensor).toString());
        }
    }
}

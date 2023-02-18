package org.example.dto;

public class MeasurementDTO {

    private double temperature;

    private boolean rain;

    private SensorDTO sensor;

    public MeasurementDTO(double temperature, boolean rain, SensorDTO sensor) {
        this.temperature = temperature;
        this.rain = rain;
        this.sensor = sensor;
    }

    public MeasurementDTO() {
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public boolean isRain() {
        return rain;
    }

    public void setRain(boolean rain) {
        this.rain = rain;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}

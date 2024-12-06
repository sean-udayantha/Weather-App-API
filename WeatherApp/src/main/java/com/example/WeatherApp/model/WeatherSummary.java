package com.example.WeatherApp.model;



import java.time.LocalDate;

public class WeatherSummary {

    private String city;
    private double averageTemperature;
    private LocalDate hottestDay;
    private LocalDate coldestDay;

    public WeatherSummary(String city, double averageTemperature, LocalDate hottestDay, LocalDate coldestDay) {
        this.city = city;
        this.averageTemperature = averageTemperature;
        this.hottestDay = hottestDay;
        this.coldestDay = coldestDay;
    }

    public String getCity() {
        return city;
    }

    public double getAverageTemperature() {
        return averageTemperature;
    }

    public LocalDate getHottestDay() {
        return hottestDay;
    }

    public LocalDate getColdestDay() {
        return coldestDay;
    }
}

package com.example.WeatherApp.controller;
import com.example.WeatherApp.model.WeatherSummary;
import com.example.WeatherApp.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public ResponseEntity<WeatherSummary> getWeatherSummary(@RequestParam String city) {
        WeatherSummary summary = weatherService.getWeatherSummary(city);
        return ResponseEntity.ok(summary);
    }
}

package com.example.WeatherApp.service;

import com.example.WeatherApp.exception.ApiCallException;
import com.example.WeatherApp.model.WeatherApiResponse;
import com.example.WeatherApp.model.WeatherSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class WeatherService {

    private final WebClient webClient;
//    private final String apiKey = "735c331894baa61d0a487c98d555575f";
//    private final String baseUrl = "https://api.openweathermap.org/data/2.5/forecast";


    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Value("${openweathermap.api.url}")
    private String baseUrl;

    public WeatherService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Cacheable(value = "weatherSummary", key = "#city", unless = "#result == null")
    public WeatherSummary getWeatherSummary(String city) {
        try {
            // Fetch data asynchronously
            CompletableFuture<WeatherApiResponse> responseFuture = fetchWeatherData(city);

            // Process the data
            WeatherApiResponse response = responseFuture.join(); // Block until data is available

            List<WeatherApiResponse.Forecast> forecasts = response.getList();

            double averageTemperature = forecasts.stream()
                    .mapToDouble(f -> f.getMain().getTemp())
                    .average()
                    .orElseThrow(() -> new ApiCallException("No temperature data available"));

            WeatherApiResponse.Forecast hottestDay = forecasts.stream()
                    .max(Comparator.comparingDouble(f -> f.getMain().getTemp()))
                    .orElseThrow(() -> new ApiCallException("No temperature data available"));

            WeatherApiResponse.Forecast coldestDay = forecasts.stream()
                    .min(Comparator.comparingDouble(f -> f.getMain().getTemp()))
                    .orElseThrow(() -> new ApiCallException("No temperature data available"));

            return new WeatherSummary(
                    city,
                    averageTemperature,
                    LocalDate.parse(hottestDay.getDt_txt().split(" ")[0]),
                    LocalDate.parse(coldestDay.getDt_txt().split(" ")[0])
            );
        } catch (Exception e) {
            throw new ApiCallException("Error processing weather data for city: " + city);
        }
    }

    @Async
    public CompletableFuture<WeatherApiResponse> fetchWeatherData(String city) {
        try {
            String url = String.format("%s?q=%s&appid=%s&units=metric", baseUrl, city, apiKey);

            WeatherApiResponse response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(WeatherApiResponse.class)
                    .block(); // Blocking here because WebClient is synchronous in this context

            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            throw new ApiCallException("Failed to fetch weather data for city: " + city);
        }
    }
}


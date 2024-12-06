package com.example.WeatherApp.service;



import com.example.WeatherApp.exception.ApiCallException;
import com.example.WeatherApp.model.WeatherApiResponse;
import com.example.WeatherApp.model.WeatherSummary;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WeatherServiceTest {

    @Mock
    private WebClient webClient;

    @InjectMocks
    private WeatherService weatherService;

    public WeatherServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWeatherSummary() {
        // Mock API response
        WeatherApiResponse mockResponse = new WeatherApiResponse();
        // Populate mockResponse with test data (use real data structure)

        // Mock WebClient behavior
        when(webClient.get()
                .uri(anyString())
                .retrieve()
                .bodyToMono(WeatherApiResponse.class)
                .block()).thenReturn(mockResponse);

        // Call service method
        WeatherSummary summary = weatherService.getWeatherSummary("London");

        // Validate results
        assertEquals("London", summary.getCity());
        // Add more assertions based on your mock data
    }

    @Test
    public void testApiCallException() {
        // Simulate API failure
        when(webClient.get()
                .uri(anyString())
                .retrieve()
                .bodyToMono(WeatherApiResponse.class)
                .block()).thenThrow(new RuntimeException("API call failed"));

        // Assert exception is thrown
        assertThrows(ApiCallException.class, () -> weatherService.getWeatherSummary("InvalidCity"));
    }

    @Test
    public void testFetchWeatherDataAsync() throws Exception {
        // Mock API response
        WeatherApiResponse mockResponse = new WeatherApiResponse();
        // Populate mockResponse with data

        // Mock WebClient behavior
        when(webClient.get()
                .uri(anyString())
                .retrieve()
                .bodyToMono(WeatherApiResponse.class)
                .block()).thenReturn(mockResponse);

        // Call the async method
        CompletableFuture<WeatherApiResponse> future = weatherService.fetchWeatherData("London");

        // Verify the result
        assertNotNull(future.get()); // Wait for async completion
    }

}

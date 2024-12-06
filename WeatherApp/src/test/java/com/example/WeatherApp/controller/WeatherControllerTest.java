package com.example.WeatherApp.controller;



import com.example.WeatherApp.model.WeatherSummary;
import com.example.WeatherApp.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    public void testGetWeatherSummary() throws Exception {
        // Mock data
        WeatherSummary mockSummary = new WeatherSummary(
                "London", 15.5, LocalDate.parse("2024-11-20"), LocalDate.parse("2024-11-18")
        );

        // Mock service behavior
        Mockito.when(weatherService.getWeatherSummary(anyString())).thenReturn(mockSummary);

        // Perform GET request and assert the results
        mockMvc.perform(get("/weather").param("city", "London"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("London"))
                .andExpect(jsonPath("$.averageTemperature").value(15.5))
                .andExpect(jsonPath("$.hottestDay").value("2024-11-20"))
                .andExpect(jsonPath("$.coldestDay").value("2024-11-18"));
    }
}


package com.example.WeatherApp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/test")
public class TestController {
    @GetMapping("/test1")
    public String test() {
        System.out.println("test");
        return "This is a test endpoint!";
    }
    @GetMapping("/home")
    public String home() {
        System.out.println("home");
        return "Welcome to the home page!";
    }
}

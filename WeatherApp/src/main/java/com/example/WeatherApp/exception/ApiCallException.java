package com.example.WeatherApp.exception;



public class ApiCallException extends RuntimeException {

    public ApiCallException(String message) {
        super(message);
    }
}
package com.aeron.weatherapp.model;

import lombok.Data;

@Data
public class WeatherInformationResponse {
    private String day;
    private Integer temperature;
    private String wind;
}

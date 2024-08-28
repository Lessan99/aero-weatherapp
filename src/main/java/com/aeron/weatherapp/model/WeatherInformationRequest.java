package com.aeron.weatherapp.model;

import lombok.Data;

@Data
public class WeatherInformationRequest {
    private int lattitude;
    private int longitude;
}

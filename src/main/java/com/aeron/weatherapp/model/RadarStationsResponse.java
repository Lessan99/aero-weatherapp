package com.aeron.weatherapp.model;

import lombok.Data;

@Data
public class RadarStationsResponse {
    private String radarId;
    private String lattitude;
    private String longitude;
}
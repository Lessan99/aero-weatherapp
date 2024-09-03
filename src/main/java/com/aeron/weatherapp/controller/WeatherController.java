package com.aeron.weatherapp.controller;

import com.aeron.weatherapp.model.RadarStationsResponse;
import com.aeron.weatherapp.model.WeatherInformationRequest;
import com.aeron.weatherapp.model.WeatherInformationResponse;
import com.aeron.weatherapp.util.WeatherAppConstants;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WeatherController {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/forecast")
    public ResponseEntity<List<WeatherInformationResponse>> getWeatherInformation(@RequestBody WeatherInformationRequest weatherInformationRequest) {
        String json=restTemplate.getForObject(String.format(WeatherAppConstants.weatherApiForecastUrl,weatherInformationRequest.getLattitude(),weatherInformationRequest.getLongitude()),String.class);
        JSONArray periods = new JSONObject(json).getJSONObject("properties").getJSONArray("periods");
        List<WeatherInformationResponse> sevenDayforecast=new ArrayList<>();
        for (int i=0;i< periods.length();i++){
            WeatherInformationResponse weatherInformationResponse = new WeatherInformationResponse();
            weatherInformationResponse.setDay(periods.getJSONObject(i).getString("name"));
            weatherInformationResponse.setTemperature(periods.getJSONObject(i).getInt("temperature"));
            weatherInformationResponse.setWind(periods.getJSONObject(i).get("windSpeed")+" "+periods.getJSONObject(i).get("windDirection"));
            sevenDayforecast.add(weatherInformationResponse);
        }
        return new ResponseEntity<>(sevenDayforecast, HttpStatus.OK);
    }
    @GetMapping("/stations")
    public ResponseEntity<List<RadarStationsResponse>> getRadarStations() {
        String json=restTemplate.getForObject(WeatherAppConstants.weatherApiRadarStationsUrl,String.class);
        JSONArray stations = new JSONObject(json).getJSONArray(WeatherAppConstants.FEATURES);
        List<RadarStationsResponse> stationsInformation = new ArrayList<>();
        for (int i=0;i< stations.length();i++){
            RadarStationsResponse radarStationsResponse = new RadarStationsResponse();
            radarStationsResponse.setRadarId(new JSONObject(stations.get(i).toString()).get(WeatherAppConstants.ID).toString());
            radarStationsResponse.setLattitude(new JSONObject(new JSONObject(stations.get(i).toString()).get(WeatherAppConstants.GEOMETRY).toString()).getJSONArray(WeatherAppConstants.COORDINATES).get(0).toString());
            radarStationsResponse.setLongitude(new JSONObject(new JSONObject(stations.get(i).toString()).get(WeatherAppConstants.GEOMETRY).toString()).getJSONArray(WeatherAppConstants.COORDINATES).get(1).toString());
            stationsInformation.add(radarStationsResponse);
        }
        return new ResponseEntity<>(stationsInformation,HttpStatus.OK);
    }
    

}
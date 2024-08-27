package com.aeron.weatherapp.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class WeatherController {


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/employees")
    String all() {
        return "Hello world";
    }

}
package com.ibm3.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibm3.service.WeatherService;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getWeather(@QueryParam("city") String city) {
        return weatherService.getWeatherByCity(city);
    }
	
}

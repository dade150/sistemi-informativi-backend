package com.ibm3.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibm3.service.WeatherService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("weather")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WeatherController {
	
	@Autowired
	private WeatherService weatherService;

    @GET
    @Path("{city}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getWeather(@PathParam("city") String city) {
        return weatherService.getWeatherByCity(city);
    }
	
}

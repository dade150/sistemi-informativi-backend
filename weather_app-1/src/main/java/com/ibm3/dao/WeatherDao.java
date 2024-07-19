package com.ibm3.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

@Repository
public class WeatherDao {

	@Autowired
	private WebClient webClient;
	
    public String getWeatherData(String apiUrl) {
        String weatherData = webClient
            .get()
            .uri(apiUrl)
            .retrieve()
            .bodyToMono(String.class)
            .block();
        return weatherData;
    }

}

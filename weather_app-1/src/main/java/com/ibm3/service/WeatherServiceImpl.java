package com.ibm3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ibm3.dao.WeatherDao;

@Service
public class WeatherServiceImpl implements WeatherService {
	
	@Autowired
	private WeatherDao weatherDao;
	
    @Override
    public String getWeatherByCity(String city) {
        String apiUrl = "http://api.weatherapi.com/v1/current.json?key=5f39cad7c66d491fb79120019241907&q="+city+"&aqi=no"; // Replace with actual API URL

        String weatherData = weatherDao.getWeatherData(apiUrl);

        return weatherData;
    }

}

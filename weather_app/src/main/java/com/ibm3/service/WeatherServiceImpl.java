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
		return weatherDao.getWeatherByCity(city);
	}



}

package com.ibm3.service;

import java.util.List;

import com.ibm3.dto.CorsoDto;

public interface CorsoService {

	/*CorsoDto getCourseById(int id);
	Corso createCourse(CorsoDto corsoDto);*/
	List<CorsoDto> getCourses();
	/*void updateCourse(CorsoDtoAggiornamento corsoDto);
	void deleteCourse(int id);*/
	
}

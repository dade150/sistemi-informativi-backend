package com.ibm3.service;

import java.util.List;

import com.ibm3.model.Course;
import com.ibm3.model.course_dto.CourseDto;
import com.ibm3.model.course_dto.CourseRegistrationDto;

public interface CourseService {
	
	CourseDto getCourseById(int id);
	Course createCourse(CourseRegistrationDto courseDto);
	List<CourseDto> getCourses();
//	void updateCourse(CourseUpdateDto);
//	void deleteCourse(int id);

}

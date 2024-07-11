package com.ibm3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm3.dao.CourseDao;
import com.ibm3.model.Category;
import com.ibm3.model.Course;
import com.ibm3.model.User;
import com.ibm3.model.course_dto.CourseDto;
import com.ibm3.model.course_dto.CourseRegistrationDto;

@Service
public class CourseServiceImpl implements CourseService{

	@Autowired
	private CourseDao courseDao;

	@Override
	public CourseDto getCourseById(int id) {

		Optional<Course> optionalCourse = courseDao.findById(id);	
		CourseDto courseDto = new CourseDto();
		
		if (optionalCourse.isPresent()) {
			
			Course course= optionalCourse.get();
			ModelMapper modelMapper = new ModelMapper();
			
			modelMapper.map(course, courseDto);
		}
		
		return courseDto;
	}

	@Override
	public List<CourseDto> getCourses() {

		List<Course> courses = (List<Course>) courseDao.findAll();
		ModelMapper modelMapper= new ModelMapper();
		List<CourseDto> coursesDto= new ArrayList<>();
		
		for(Course course: courses) {
			
			CourseDto courseDto = new CourseDto();
			modelMapper.map(course, courseDto);
			
			coursesDto.add(courseDto);
			
		}
		return coursesDto;
	}

	@Override
	public Course createCourse(CourseRegistrationDto courseDto) {
	    
		Course course = new Course();
	    
	    course.setName(courseDto.getName());
	    course.setCompleteDescription(courseDto.getCompleteDescription());
	    course.setDuration(courseDto.getDuration());
	    course.setSmallDescription(courseDto.getSmallDescription());

	    Category category = new Category();
	    ModelMapper modelMapper = new ModelMapper();
	    modelMapper.map(courseDto.getCategory(), category);
	    
	    course.setCategory(category);

	    try {
	        courseDao.save(course);    
	    } catch (Exception e) {
	        e.printStackTrace();  // Log dell'eccezione per debug
	        throw e;  // Rilancia l'eccezione per gestirla a livello superiore
	    }
	    
	    return course;
	}



}

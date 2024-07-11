package com.ibm3.dao;

import org.springframework.data.repository.CrudRepository;

import com.ibm3.model.Course;

public interface CourseDao extends CrudRepository<Course, Integer> {

}

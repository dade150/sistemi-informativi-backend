package com.ibm3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import com.ibm3.annotation.JWTTokenNeeded;
import com.ibm3.annotation.Secured;
import com.ibm3.model.Course;
import com.ibm3.model.course_dto.CourseDto;
import com.ibm3.model.course_dto.CourseRegistrationDto;
import com.ibm3.model.user_dto.UserRegistrationDto;
import com.ibm3.service.CourseService;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

//@Secured(role = "Admin")
//@JWTTokenNeeded
@Path("/course")
public class CourseController {
	
	@Autowired
	CourseService courseService;
	
    @GET
    @Path("/getcourse/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Response getUserByEmail(@PathParam("id") int id) {
    	
    	CourseDto courseDto = courseService.getCourseById(id);
    	
	    if (courseDto!=null) {
	    	
	    	System.out.println(courseDto);
	    	return Response.status(Response.Status.OK).entity(courseDto).build();
	    	
	    } else {
	    	
	    	return Response.status(Response.Status.BAD_REQUEST).entity("Course not found").build();
	    }
    }
    
    @GET
    @Path("/getcourses")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Response getAllCourses() {
    	
    	List<CourseDto> courses=courseService.getCourses();
    	
	    if (courses!=null) {

	    	return Response.status(Response.Status.OK).entity(courses).build();
	    	
	    } else {
	    	
	    	return Response.status(Response.Status.BAD_REQUEST).entity("Courses not found").build();
	    }
    	
    }
    
    @POST
    @Path("/createcourse")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Response createCourse(@RequestBody CourseRegistrationDto courseDto) {
    	
    	Course course = courseService.createCourse(courseDto);
    	
    	if(course!=null) {
    		
    		return Response.status(Response.Status.OK).entity(course).build();
    		
    	}else {
    		
	    	return Response.status(Response.Status.BAD_REQUEST).entity("Courses not create").build();
    		
    	}
    }

}

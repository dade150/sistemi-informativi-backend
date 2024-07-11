package com.ibm3.model.user_dto;

import java.util.ArrayList;
import java.util.List;

import com.ibm3.model.course_dto.CourseDto;
import com.ibm3.model.role_dto.RoleDto;

public class UserDto {

	private int id;
	
	private String name;
	
	private String lastname;
	
	private String email;
	
	private List<RoleDto> roles = new ArrayList<>();
	
	private List<CourseDto> courses = new ArrayList<>();

	public List<CourseDto> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseDto> courses) {
		this.courses = courses;
	}

	public List<RoleDto> getRoles() { 
		return roles;
	}

	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", name=" + name + ", lastname=" + lastname + ", email=" + email + ", roles="
				+ roles + ", courses=" + courses + "]";
	}
}

package com.ibm3.model.course_dto;


public class CourseDto {

	private int id;
	
	private String name;
	
	private String smallDescription;

	public String getSmallDescription() {
		return smallDescription;
	}

	public void setSmallDescription(String smallDescription) {
		this.smallDescription = smallDescription;
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
	
		@Override
	public String toString() {
		return "CourseDto [id=" + id + ", name=" + name + "]";
	}

}

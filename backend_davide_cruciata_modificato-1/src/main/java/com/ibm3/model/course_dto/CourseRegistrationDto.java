package com.ibm3.model.course_dto;

import com.ibm3.model.category_dto.CategoryDto;

public class CourseRegistrationDto {

	private int id;

	private String name;

	private String smallDescription;

	private String completeDescription;

	private int duration;
	
	private CategoryDto category;


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

	public String getSmallDescription() {
		return smallDescription;
	}

	public void setSmallDescription(String smallDescription) {
		this.smallDescription = smallDescription;
	}

	public String getCompleteDescription() {
		return completeDescription;
	}

	public void setCompleteDescription(String completeDescription) {
		this.completeDescription = completeDescription;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public CategoryDto getCategory() {
		return category;
	}

	public void setCategory(CategoryDto category) {
		this.category = category;
	}

}

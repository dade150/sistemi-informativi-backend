package com.ibm3.service;

import java.util.List;

import com.ibm3.exception.ObjectNotFound;
import com.ibm3.exception.UnauthorizedException;
import com.ibm3.model.category_dto.CategoryDto;


public interface CategoryService  {

	List<CategoryDto> getAllCategories();
	
	void delete (int id) throws ObjectNotFound, UnauthorizedException ;
	
}

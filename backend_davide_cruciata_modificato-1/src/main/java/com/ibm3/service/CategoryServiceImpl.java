package com.ibm3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm3.dao.CategoryDao;
import com.ibm3.exception.ObjectNotFound;
import com.ibm3.exception.UnauthorizedException;
import com.ibm3.model.Category;
import com.ibm3.model.category_dto.CategoryDto;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryDao categoryDao;

	@Override
	public List<CategoryDto> getAllCategories() {
		
		List<Category> categories = (List<Category>) categoryDao.findAll();
		List<CategoryDto> categoriesDto = new ArrayList<>();
		
		ModelMapper modelMapper = new ModelMapper();
		
		for(Category category : categories) {
			
			CategoryDto categoryDto= new CategoryDto();
			modelMapper.map(category, categoryDto);
			
			categoriesDto.add(categoryDto);
			
		}
		
		return categoriesDto;
	}

	  @Override
	  public void delete(int id) throws ObjectNotFound, UnauthorizedException {
	    Optional<Category> categoryOptional = categoryDao.findById(id);
	    if (!categoryOptional.isEmpty()) {
	      Category category = categoryOptional.get();
	      if (!category.getCourses().isEmpty()) {
	        categoryDao.delete(category);
	      }
	      else {
	        throw new UnauthorizedException("non autorizzato");
	      }
	    }
	    else {
	      throw new ObjectNotFound("non trovato");
	    }
	  }
}

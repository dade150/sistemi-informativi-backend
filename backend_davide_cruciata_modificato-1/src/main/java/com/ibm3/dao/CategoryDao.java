package com.ibm3.dao;

import org.springframework.data.repository.CrudRepository;

import com.ibm3.model.Category;

public interface CategoryDao extends CrudRepository<Category, Integer> {

}
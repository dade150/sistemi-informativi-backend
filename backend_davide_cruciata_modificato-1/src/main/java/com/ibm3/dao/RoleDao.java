package com.ibm3.dao;

import org.springframework.data.repository.CrudRepository;

import com.ibm3.model.Role;

public interface RoleDao extends CrudRepository<Role, Integer> {

}

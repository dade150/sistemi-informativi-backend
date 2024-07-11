package com.ibm3.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ibm3.model.User;

public interface UserDao extends CrudRepository<User, Integer> {

	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

}

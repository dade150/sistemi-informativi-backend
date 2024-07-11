package com.ibm3.service;

import java.util.List;


import com.ibm3.model.User;
import com.ibm3.model.user_dto.UserDto;
import com.ibm3.model.user_dto.UserLoginDto;
import com.ibm3.model.user_dto.UserRegistrationDto;
import com.ibm3.model.user_dto.UserUpdateDto;

public interface UserService {
	
	void userRegistration(UserRegistrationDto userDto);
	void updateUserData(UserUpdateDto userDto);
	List<UserDto> getUsers();
	boolean existUser(String email);
	boolean login(UserLoginDto userDto);
	UserDto getUserByEmail(String email);
	User findbyEmail(String email);
	void deleteUser(String email);
	
	//da rifare nel courseService
	void addCourse(int idUser,int idCourse);
	void deleteCourse(int idUser,int idCourse);
	
}
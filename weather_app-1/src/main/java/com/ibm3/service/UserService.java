package com.ibm3.service;

import com.ibm3.model.dto.UserDto;
import com.ibm3.model.dto.UserLoginDto;
import com.ibm3.model.dto.UserRegistrationDto;

public interface UserService {
	
	void userRegistration(UserRegistrationDto userDto);
	boolean existUser(String email);
	UserDto login(UserLoginDto userDto);
	
	/*void updateUserData(UserUpdateDto userDto);
	UserDto getUserByEmail(String email);
	void deleteUser(String email);*/

}

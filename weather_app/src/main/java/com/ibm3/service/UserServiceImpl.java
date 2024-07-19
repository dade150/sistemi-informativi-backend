package com.ibm3.service;

import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm3.dao.UserDao;
import com.ibm3.model.User;
import com.ibm3.model.dto.UserDto;
import com.ibm3.model.dto.UserLoginDto;
import com.ibm3.model.dto.UserRegistrationDto;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;

	@Override
	public void userRegistration(UserRegistrationDto userDto) {
		
		String sha256Hex = DigestUtils.sha256Hex(userDto.getPassword());
		userDto.setPassword(sha256Hex);
		
		User user = new User();
		
		ModelMapper modelMapper = new ModelMapper();    	
    	modelMapper.map(userDto,user );
    	
    	try {
    		if(!existUser(user.getEmail())) {
    			userDao.save(user);
    		}//else lancia eccezione utente gia registrato
    		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
	
	@Override
	public boolean existUser(String email) {
		return userDao.existsByEmail(email);
	}	
	
	@Override
	public UserDto login(UserLoginDto userLoginDto) {
		
		String email = userLoginDto.getEmail();
		String sha256Hex = DigestUtils.sha256Hex(userLoginDto.getPassword());
		
		Optional<User> optionalUser = userDao.findByEmailAndPassword(email, sha256Hex);
		
		if(optionalUser.isPresent()) {

			User user = optionalUser.get();
			UserDto userDto= new UserDto();
			ModelMapper modelMapper = new ModelMapper(); 
			modelMapper.map(user,userDto);
			
			return userDto;

		} // else trona eccezione
		else {
			return null;
		}
	}
/*
	@Override
	public void updateUserData(UserUpdateDto userDto) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public UserDto getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(String email) {
		// TODO Auto-generated method stub
		
	}*/

}

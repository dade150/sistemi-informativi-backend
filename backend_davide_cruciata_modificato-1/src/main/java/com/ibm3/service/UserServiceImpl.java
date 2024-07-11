package com.ibm3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm3.dao.CourseDao;
import com.ibm3.dao.UserDao;
import com.ibm3.model.role_dto.RoleDto;
import com.ibm3.model.Course;
import com.ibm3.model.Role;
import com.ibm3.model.User;
import com.ibm3.model.course_dto.CourseDto;
import com.ibm3.model.user_dto.UserDto;
import com.ibm3.model.user_dto.UserLoginDto;
import com.ibm3.model.user_dto.UserRegistrationDto;
import com.ibm3.model.user_dto.UserUpdateDto;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CourseDao courseDao;

	@Override
	public void userRegistration(UserRegistrationDto userDto) {

		String sha256Hex = DigestUtils.sha256Hex(userDto.getPassword());
		userDto.setPassword(sha256Hex);
		
		User user = new User();
		
		ModelMapper modelMapper = new ModelMapper();    	
    	modelMapper.map(userDto,user );
		
		userDao.save(user);
	}
	
	@Override
	public List<UserDto> getUsers() {
		
		List<UserDto> usersDto= new ArrayList<>();
		
		List<User> users = (List<User>) userDao.findAll();
		
		//System.out.println(users);
		
		for(User user : users) {
			
			UserDto userDto = new UserDto();
		
			userDto.setName(user.getName());
			userDto.setLastname(user.getLastname());
			userDto.setId(user.getId());
			userDto.setEmail(user.getEmail());
			
			
			for(Course course : user.getCourses()) {
				
				CourseDto courseDto = new CourseDto();
				courseDto.setId(course.getId());
				courseDto.setName(course.getName());
				userDto.getCourses().add(courseDto);
				
			}
			for(Role role : user.getRoles()) {
				
				RoleDto roleDto = new RoleDto();
				roleDto.setId(role.getId());
				roleDto.setRoleName(role.getRoleName());
				userDto.getRoles().add(roleDto);
				
			}
			
			usersDto.add(userDto);
			
		}
		
		System.out.println(usersDto);
		
		return usersDto;
	}
	
	@Override
	public void updateUserData(UserUpdateDto userDto) {
		
		Optional<User> optionalUser = userDao.findByEmail(userDto.getOldEmail());
		
		if(optionalUser.isPresent()){
			
			User user = optionalUser.get();
			//System.out.println(user);
			
			if (userDto.getName()!=null) {
				user.setName(userDto.getName());
			}
			
			if (userDto.getLastname()!=null) {
				user.setLastname(userDto.getLastname());
			}
			
			if (userDto.getPassword()!=null) {
				String sha256hex = DigestUtils.sha256Hex(userDto.getPassword());
				user.setPassword(sha256hex);
			}
			if(userDto.getNewEmail()!=null) {
				user.setEmail(userDto.getNewEmail());
			}
			
			userDao.save(user);
		} else {
			//System.out.println("non vi è alcun userOptional");
			//si puo lanciare un eccezione di utente not found
		}
	}

	@Override
	public boolean existUser(String email) {
		
		return userDao.existsByEmail(email);
		
	}

	@Override
	public boolean login(UserLoginDto userDto) {
		
		String email = userDto.getEmail();
	    String password = userDto.getPassword();

	    Optional<User> utente = userDao.findByEmail(email);
	    
	    if (utente == null) {
	        return false;
	    }

	    String hashedPassword = DigestUtils.sha256Hex(password);
	    boolean passwordMatch = hashedPassword.equals(utente.get().getPassword());
	    
	    return passwordMatch;
	}

	@Override
	public UserDto getUserByEmail(String email) {

	    Optional<User> userOptional = userDao.findByEmail(email);
	    
	    if (userOptional.isPresent()) {
	    	
	    	User user = userOptional.get();
	        
	    	ModelMapper modelMapper = new ModelMapper();
	    	UserDto userDto = new UserDto();
	    	
	    	modelMapper.map(user, userDto);
	    	
	    	//System.out.println("sono nel useroptional present");
	    	
	        return userDto;
	    } else {
	    	//System.out.println("sono nel useroptional empty");

	        return null;
	        
	    }
	}

	@Override
	public void deleteUser(String email) {

	    Optional<User> userOptional = userDao.findByEmail(email);
	    
	    if (userOptional.isPresent()) {
	    	
	    	User user= userOptional.get();
	    	userDao.delete(user);
	    	
	    } else {
	    	//System.out.println("sono nel useroptional empty");
	        
	    }
		
	}

	//da provare
	@Override
	public void addCourse(int idUser, int idCourse) {

		
		Optional<User> optionalUser= userDao.findById(idUser);
		Optional<Course> optionalCourse= courseDao.findById(idCourse);
		
		if(optionalCourse.isPresent() && optionalUser.isPresent()) {
			
			User user = optionalUser.get();
		    Course course = optionalCourse.get();
		    
		    user.getCourses().add(course);
		    
		    userDao.save(user);
		}
	}

	@Override
	public void deleteCourse(int idUser, int idCourse) {
		
		Optional<User> optionalUser = userDao.findById(idUser);
	    Optional<Course> optionalCourse = courseDao.findById(idCourse);

	    if (optionalUser.isPresent() && optionalCourse.isPresent()) {
	        
	    	User user = optionalUser.get();
	    	Course course = optionalCourse.get();

	    // Rimuovi il corso dall'utente
	    	if (user.getCourses().contains(course)) {
	    		user.getCourses().remove(course);
	    		userDao.save(user); 
	    	}
	    		
	    }
	}

	@Override
	public User findbyEmail(String email) {
 
		Optional<User> optionalUser = userDao.findByEmail(email);

		
		return optionalUser.orElse(null);
	}


}

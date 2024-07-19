package com.ibm3.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import com.ibm3.model.dto.UserDto;
import com.ibm3.model.dto.UserLoginDto;
import com.ibm3.model.dto.UserRegistrationDto;
import com.ibm3.service.UserService;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@POST
	@Path("/reg")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response userRegistration(@Valid @RequestBody UserRegistrationDto userDto){
				
		if(!Pattern.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,20}", userDto.getPassword())) {
				
			System.out.println("password non conforme");
			return Response.status(Response.Status.BAD_REQUEST).build(); 	
		} 
		
		
		try {
			
			userService.userRegistration(userDto);
			return Response.status(Response.Status.OK).build();

		} catch (Exception e) {
			
			return Response.status(Response.Status.BAD_REQUEST).build(); 	
		}
		
	}
	
	@GET
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@RequestBody UserLoginDto userLoginDto) {
		
		try {
			UserDto userDto = userService.login(userLoginDto);
			
			return Response.status(Response.Status.OK).entity(userDto).build();

		} catch (Exception e) {
			
			return Response.status(Response.Status.BAD_REQUEST).build(); 
			
		}
		
	}

}

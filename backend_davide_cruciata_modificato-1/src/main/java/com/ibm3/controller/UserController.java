package com.ibm3.controller;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import com.ibm3.model.role_dto.RoleDto;
import com.ibm3.model.user_dto.UserDto;
import com.ibm3.model.user_dto.UserLoginDto;
import com.ibm3.model.user_dto.UserLoginResponseDto;
import com.ibm3.model.user_dto.UserRegistrationDto;
import com.ibm3.model.user_dto.UserUpdateDto;
import com.ibm3.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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
	@Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
		try {
			
            List<UserDto> usersDto = userService.getUsers();
            return Response.status(Response.Status.OK).entity(usersDto).build();            
        } catch (Exception e) {
        	
            return Response.status(Response.Status.BAD_REQUEST).entity("Errore durante il recupero degli utenti").build();
            
        }
    }
	
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@RequestBody UserUpdateDto userDto) {
		try {
			System.out.println("ei sono qui");
            userService.updateUserData(userDto);
            return Response.ok().entity("User updated successfully").build();
        
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("An error occurred").build();
        }
		
	}
	
	@GET
	@Path("/check/{email}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkUserExists(@PathParam("email") String email) {
		
	    boolean userExists = userService.existUser(email);
	    
	    if (userExists) {
	    	
	    	return Response.status(Response.Status.OK).entity("User Exist").build();	    	
	    } else {
	    	
	    	return Response.status(Response.Status.BAD_REQUEST).entity("User not found").build();
	    }
	}
	
	public UserLoginResponseDto issueToken(String email) {
		
		byte[] secret = "3davide35472803huefiri39rjiwnfonimndbnuifnqinwijbuiwendionyhbweuf".getBytes();
	    Key key = Keys.hmacShaKeyFor(secret);
	    
	    UserDto informazioniUtente = userService.getUserByEmail(email);
	    Map<String,Object> map= new HashMap<>();
	    
	    map.put("name", informazioniUtente.getName());
	    map.put("lastname", informazioniUtente.getLastname());
	    map.put("email", informazioniUtente.getEmail());
	    
	    
	    List<String> roles = new ArrayList<>();
	    for (RoleDto role: informazioniUtente.getRoles()) {
	    	
	        roles.add(role.getRoleName());
	    }
	    
	    map.put("roles", roles);
	    
	    Date creation = new Date();
	    Date end = java.sql.Timestamp.valueOf(LocalDateTime.now().plusMinutes(15L));
	    
	    String tokenJwts = Jwts.builder()
	    		.setClaims(map)
	    		.setIssuer("http://localhost:8080")
	    		.setIssuedAt(creation)
	    		.setExpiration(end)
	    		.signWith(key)
	    		.compact();
	    		
	    UserLoginResponseDto token = new UserLoginResponseDto();
	    
	    token.setToken(tokenJwts);
	    token.setTokenCreationTime(creation);
	    token.setTtl(end);
	    
	    return token;
	}
	
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@RequestBody UserLoginDto loginRequestDto) {
        try {
            if (!userService.login(loginRequestDto)) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            String email = loginRequestDto.getEmail();

            UserLoginResponseDto token = issueToken(email);

            if (token != null && token.getToken() != null && !token.getToken().isEmpty()) {
                return Response.status(Response.Status.OK).entity(token).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }

        } catch (Exception e) {
        	
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
            
        }
    }
    
    @GET
    @Path("/getuser/{email}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Response getUserByEmail(@PathParam("email") String email) {
    	
    	UserDto userDto = userService.getUserByEmail(email);
    	
	    if (userDto!=null) {
	    	
	    	System.out.println(userDto);
	    	return Response.status(Response.Status.OK).entity(userDto).build();	    	
	    } else {
	    	
	    	return Response.status(Response.Status.BAD_REQUEST).entity("User not found").build();
	    }
    }
    
    @DELETE
    @Path("delete/{email}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("email") String email){
    	
    	try {
			userService.deleteUser(email);
	    	return Response.status(Response.Status.OK).build();	    	

		} catch (Exception e) {
	    	return Response.status(Response.Status.BAD_REQUEST).entity("User not found").build();
		}

    }


    @PUT
    @Path("{id_user}/course/{id_course}/subscribe")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Response subscribe(@PathParam("id_user") int idUser, @PathParam("id_course") int idCourse){
    	
    	try {
    		
			userService.addCourse(idUser, idCourse);
            return Response.status(Response.Status.OK).build();

		} catch (Exception e) {
			
            return Response.status(Response.Status.BAD_REQUEST).build();

		}
    	
    }
    
  //da provare
    @PUT
    @Path("{id_user}/course/{id_course}/unsubscribe")
    public Response unsubscribe(@PathParam("id_user") int idUser, @PathParam("id_course") int idCourse){
    	
    	try {
    		
			userService.deleteCourse(idUser, idCourse);
            return Response.status(Response.Status.OK).build();

		} catch (Exception e) {
			
            return Response.status(Response.Status.BAD_REQUEST).build();

		}
    	
    }
    
    

}
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

import com.ibm3.dto.UtenteDto;
import com.ibm3.dto.UtenteDtoAggiornamento;
import com.ibm3.dto.UtenteLoginRequestDto;
import com.ibm3.dto.UtenteLoginResponseDto;
import com.ibm3.dto.UtenteRegistrazioneDto;
import com.ibm3.model.Ruolo;
import com.ibm3.model.Utente;
import com.ibm3.service.UtenteService;

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

@Path("/utente")
public class UtenteController {
	
	@Autowired
	private UtenteService utenteService;
	
	public UtenteLoginResponseDto issueToken(String email) {
		
		byte[] secret = "3davide35472803huefiri39rjiwnfonimndbnuifnqinwijbuiwendionyhbweuf".getBytes();
	    Key key = Keys.hmacShaKeyFor(secret);
	    
	    UtenteDto informazioniUtente = utenteService.getUserByEmail(email);
	    Map<String,Object> map= new HashMap<>();
	    
	    map.put("nome", "utente.getNome()");
	    map.put("cognome", "utente.getCognome()");
	    map.put("email", "utente.getEmail()");
	    
	    List<String> ruoli = new ArrayList<String>();
	    
	    for (Ruolo ruolo: informazioniUtente.getRuoli()) {
	    	ruoli.add(ruolo.getTipologia().name());
	    }
	    
	    Date creation = new Date();
	    Date end = java.sql.Timestamp.valueOf(LocalDateTime.now().plusMinutes(15L));
	    
	    String tokenJwts = Jwts.builder()
	    		.setClaims(map)
	    		.setIssuer("http://localhost:8080")
	    		.setIssuedAt(creation)
	    		.setExpiration(end)
	    		.signWith(key)
	    		.compact();
	    		
	    UtenteLoginResponseDto token = new UtenteLoginResponseDto();
	    
	    token.setToken(tokenJwts);
	    token.setTokenCreationTime(creation);
	    token.setTtl(end);
	    
	    return token;
	}
	
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Valid UtenteLoginRequestDto loginRequestDto) {
        try {
            if (!utenteService.login(loginRequestDto)) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            String email = loginRequestDto.getEmail();

            UtenteLoginResponseDto token = issueToken(email);

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
	
	@POST
	@Path("/reg")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response userRegistration(@Valid @RequestBody Utente utente) {

		try {			
			if(!Pattern.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,20}", utente.getPassword())) {
				System.out.println("password non conforme");
				return Response.status(Response.Status.BAD_REQUEST).build(); 	
			}
							
			/*if(utenteService.existUserByEmail(user.getEmail())) {	
				
				return Response.status(Response.Status.BAD_REQUEST).build();
				
			}*/
			UtenteRegistrazioneDto utenteDto  = new UtenteRegistrazioneDto();
			utenteDto.setNome(utente.getNome());
			utenteDto.setCognome(utente.getCognome());
			utenteDto.setEmail(utente.getEmail());
			utenteDto.setPassword(utente.getPassword());

			utenteService.userRegistration(utenteDto);
			return Response.status(Response.Status.OK).build();
	
		} catch (Exception e) {

			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@DELETE
	@Path("/delete/{email}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("email") String email) {

		try {
			utenteService.deleteUser(email);
			return Response.status(Response.Status.OK).build();

		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();	
		}
	}
	
    @GET
    @Path("/getbyemail/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByEmail(@PathParam("email") String email) {
        try {
            UtenteDto user = utenteService.getUserByEmail(email);
            if (user != null) {
                return Response.status(Response.Status.OK).entity(user).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
	
    @GET
    @Path("/exist/{email}")
    public Response existUser(@PathParam("email") String email) {
        boolean exists = utenteService.existUser(email);
        return exists ? Response.ok().entity("L'utente esiste").build() : Response.status(Response.Status.NOT_FOUND).entity("L'utente non esiste").build();
    }

	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@RequestBody UtenteDtoAggiornamento user) {

		try {
			utenteService.updateUserData(user);
			return Response.status(Response.Status.OK).build();

		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();	
		}
	}

  
}


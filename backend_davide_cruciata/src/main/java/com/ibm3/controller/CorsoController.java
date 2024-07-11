package com.ibm3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibm3.dto.CorsoDto;
import com.ibm3.jwt.JWTTokenNeeded;
import com.ibm3.jwt.Secured;
import com.ibm3.service.CorsoService;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Secured(role = "Admin")
@JWTTokenNeeded
@Path("/corso")
public class CorsoController {
	
	@Autowired
	CorsoService corsoService;
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourses() {
        try {
            List<CorsoDto> corsi = corsoService.getCourses();
            return Response.status(Response.Status.OK).entity(corsi).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Errore durante il recupero dei corsi").build();
        }
    }

}

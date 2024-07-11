package com.ibm3.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ibm3.model.Utente;

public interface UtenteDao extends CrudRepository<Utente, Integer> {

	Optional<Utente> findByEmail(String email);

}

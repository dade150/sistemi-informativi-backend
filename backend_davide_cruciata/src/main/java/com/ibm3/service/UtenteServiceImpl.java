package com.ibm3.service;

import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm3.dao.UtenteDao;
import com.ibm3.dto.UtenteDto;
import com.ibm3.dto.UtenteDtoAggiornamento;
import com.ibm3.dto.UtenteLoginRequestDto;
import com.ibm3.dto.UtenteRegistrazioneDto;
import com.ibm3.model.Utente;

@Service
public class UtenteServiceImpl implements UtenteService {
	
	@Autowired
	private UtenteDao utenteDao;

    @Override
    public UtenteDto getUserByEmail(String email) {
        Optional<Utente> optionalUser = utenteDao.findByEmail(email);
        if (optionalUser.isPresent()) {
            Utente utente = optionalUser.get();
            UtenteDto utenteDto = new UtenteDto();
            utenteDto.setNome(utente.getNome());
            utenteDto.setCognome(utente.getCognome());
            utenteDto.setEmail(utente.getEmail());
            utenteDto.setRuoli(utente.getRuoli());
            
            return utenteDto;
        } else {
            return null;
        }
    }

	@Override
	public void userRegistration(UtenteRegistrazioneDto utenteDto) {
		
		String sha256hex =DigestUtils.sha256Hex(utenteDto.getPassword());
		utenteDto.setPassword(sha256hex);
		Utente utente = new Utente();
		utente.setNome(utenteDto.getNome());
		utente.setCognome(utenteDto.getCognome());
		utente.setEmail(utenteDto.getEmail());
		utente.setPassword(sha256hex);

		utenteDao.save(utente);

	}

	//qualche problemino
	@Override
	public void updateUserData(UtenteDtoAggiornamento utenteDto) {
		
		Optional<Utente> optionalUser =  utenteDao.findByEmail(utenteDto.getEmail());
		String sha256hex =DigestUtils.sha256Hex(utenteDto.getPassword());
		
		if (optionalUser.isPresent()) {

			Utente utente = optionalUser.get();
				
			utente.setNome(utente.getNome());
			utente.setCognome(utente.getCognome());
			utente.setEmail(utente.getEmail());
			utente.setPassword(sha256hex);
			
			utenteDao.save(utente);
		}
	}
/*
	@Override
	public List<UtenteDto> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public boolean existUser(String email) {
		
	    Optional<Utente> utenteOptional = utenteDao.findByEmail(email);
	    return utenteOptional.isPresent();
	    
	}

	@Override
	public boolean login(UtenteLoginRequestDto utenteLoginRequestDto) {
		
	    String email = utenteLoginRequestDto.getEmail();
	    String password = utenteLoginRequestDto.getPassword();

	    Utente utente = findByEmail(email);
	    
	    if (utente == null) {
	        return false;
	    }

	    String hashedPassword = DigestUtils.sha256Hex(password);
	    boolean passwordMatch = hashedPassword.equals(utente.getPassword());
	    
	    return passwordMatch;
	}

    @Override
    public Utente findByEmail(String email) {
        Optional<Utente> optionalUtente = utenteDao.findByEmail(email);
        return optionalUtente.orElse(null); 
    }
    
	@Override
	public void deleteUser(String email) {
		
		Optional<Utente> optionalUser = utenteDao.findByEmail(email);
		
		if(optionalUser.isPresent()) {
			utenteDao.delete(optionalUser.get());
		}		
	}

}

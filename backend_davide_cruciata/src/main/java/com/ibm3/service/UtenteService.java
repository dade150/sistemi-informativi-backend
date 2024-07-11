package com.ibm3.service;

import com.ibm3.dto.UtenteDto;
import com.ibm3.dto.UtenteDtoAggiornamento;
import com.ibm3.dto.UtenteLoginRequestDto;
import com.ibm3.dto.UtenteRegistrazioneDto;
import com.ibm3.model.Utente;

public interface UtenteService {

	void updateUserData(UtenteDtoAggiornamento utenteDto);
	/*List<UtenteDto> getUsers();*/
	boolean existUser(String email);
	boolean login(UtenteLoginRequestDto utenteLoginequestDto);
	Utente findByEmail(String email);
	UtenteDto getUserByEmail(String email);
	void deleteUser(String email);
	void userRegistration(UtenteRegistrazioneDto utenteDto);

}

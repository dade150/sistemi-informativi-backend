package com.ibm3.dto;

import java.util.ArrayList;
import java.util.List;

import com.ibm3.model.Ruolo;


public class UtenteDto {
	
	private int id;
    private String nome;
    private String cognome;
    private String email;
	private List<Ruolo> ruoli = new ArrayList<>();
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Ruolo> getRuoli() {
		return ruoli;
	}
	public void setRuoli(List<Ruolo> ruoli) {
		this.ruoli = ruoli;
	}
}

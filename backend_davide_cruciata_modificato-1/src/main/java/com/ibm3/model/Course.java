package com.ibm3.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "corso")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_C")
	private int id;
	
	@Column(name = "Nome_Corso")
	private String name;
	
	@Column(name = "Descrizione_breve")
	private String smallDescription;
	
	@Column(name = "Descrizione_completa")
	private String completeDescription;
	
	@Column(name = "Durata")
	private int duration;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(
			name = "FK_CA",
			referencedColumnName = "ID_CA")
	private Category category;
	
	@ManyToMany
	@JoinTable(
			name= "utenti_corsi",
			joinColumns = @JoinColumn(name = "FK_CU", referencedColumnName = "ID_C"),
			inverseJoinColumns = @JoinColumn(name = "FK_UC", referencedColumnName = "ID_U")
			)
	private List<User> users;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSmallDescription() {
		return smallDescription;
	}

	public void setSmallDescription(String smallDescription) {
		this.smallDescription = smallDescription;
	}

	public String getCompleteDescription() {
		return completeDescription;
	}

	public void setCompleteDescription(String completeDescription) {
		this.completeDescription = completeDescription;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
		
}

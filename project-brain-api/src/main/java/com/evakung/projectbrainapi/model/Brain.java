package com.evakung.projectbrainapi.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(exclude = {"ideas", "followers", "todos"})
public class Brain {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private long id;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(unique = true, nullable = false)
	private String username;
	
	// TODO: Changed to passHash when we work on security
	@Column(unique = false, nullable = false)
	private String password;
	
	// Whatever info u want
	// TODO: country or location
	@Column(unique = false, nullable = true)
	private String firstName;	
	@Column(unique = false, nullable = true)
	private String lastName;
	
	@OneToMany
	@JsonIgnore
	private Set<Idea> ideas;
	
	// TODO: This will be a loop, need to think if it can store to db
	@ManyToMany
	@JsonIgnore
	private Set<Brain> followers;
	
	@OneToMany
	@JsonIgnore
	private Set<Idea> todos;

}

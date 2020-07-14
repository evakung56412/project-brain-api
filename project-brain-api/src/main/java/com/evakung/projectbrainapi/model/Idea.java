package com.evakung.projectbrainapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(exclude = "author")
public class Idea {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private long id;
	
	@Column(unique = false, nullable = false)
	private String title;
	
	@Column(unique = false, nullable = false)
	private String context;
	
	@Column(unique = false, nullable = false)
	private String content;
	
	@ManyToOne
	private Brain author;
}

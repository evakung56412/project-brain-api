package com.evakung.projectbrainapi.form;

import javax.persistence.Column;

import lombok.Data;

@Data
public class BrainFollwersForm {
	
	@Column(name = "username")
	private String followerUsername;

	@Column(name = "username")
	private String followedUsername;
}

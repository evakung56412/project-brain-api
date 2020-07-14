package com.evakung.projectbrainapi.form;

import lombok.Data;

@Data
public class BrainUpdateForm {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
}

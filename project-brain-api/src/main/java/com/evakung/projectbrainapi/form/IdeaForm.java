package com.evakung.projectbrainapi.form;

import lombok.Data;

@Data
public class IdeaForm {
	private String username;
	private String citeId;
	private String title;
	private String context;
	private String content;
}

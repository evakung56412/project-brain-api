package com.evakung.projectbrainapi.form;

import lombok.Data;

@Data
public class RemoveIdeaForm {
	private String citeId;
	private String title;
	private String context;
	private String content;
}

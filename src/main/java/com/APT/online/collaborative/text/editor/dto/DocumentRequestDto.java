package com.APT.online.collaborative.text.editor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


@Getter
public class DocumentRequestDto {
	@JsonProperty("documentName")
	private String documentName;

	@JsonProperty("username")
	private String username;
}

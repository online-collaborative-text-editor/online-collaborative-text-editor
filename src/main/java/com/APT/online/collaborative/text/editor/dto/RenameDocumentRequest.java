package com.APT.online.collaborative.text.editor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class RenameDocumentRequest {
	@JsonProperty("newDocumentName")
	private String newDocumentName;
}

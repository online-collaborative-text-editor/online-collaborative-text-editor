package com.APT.online.collaborative.text.editor.dto;

import lombok.Data;

@Data
public class AuthResponseDto {
	private String accessToken;
	private String tokenType = "Bearer ";

	public AuthResponseDto(String accessToken) {
		this.accessToken = accessToken;
	}
}

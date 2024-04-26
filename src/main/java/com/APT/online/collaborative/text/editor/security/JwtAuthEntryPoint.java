package com.APT.online.collaborative.text.editor.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
//	@Override
//	public void commence(HttpServletRequest request, HttpServletResponse response,
//	                     AuthenticationException authException) throws IOException, ServletException {
//		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
//	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authException) throws IOException, ServletException {
		System.out.println("Request URI: " + request.getRequestURI());
		System.out.println("Auth Exception: " + authException.getMessage());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
	}
}

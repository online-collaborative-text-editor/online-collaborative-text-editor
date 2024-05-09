package com.APT.online.collaborative.text.editor.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtGenerator {
	private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expirationDate = new Date(currentDate.getTime() + SecurityConstants.JwtExpiration);

		String token = Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(expirationDate)
				.signWith(secretKey, SignatureAlgorithm.HS512)
				.compact();
		System.out.println("Token generated: ");
		System.out.println(token);
		return token;
	}


	public String getUsernameFromJwt(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}

	public boolean validateJwt(String token) {
		try {
			Jwts.parser()
					.setSigningKey(secretKey)
					.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			// Dummy commit to trigger changes.
			throw new AuthenticationCredentialsNotFoundException("JWT was expired or invalid");
		}
	}
}

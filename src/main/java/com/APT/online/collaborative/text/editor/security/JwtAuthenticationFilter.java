package com.APT.online.collaborative.text.editor.security;

import com.APT.online.collaborative.text.editor.Model.UserEntity;

import com.APT.online.collaborative.text.editor.Repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtGenerator tokenGenerator;
	@Autowired
	private CustomUsersDetailsService customUsersDetailsService;

	@Autowired
	private UserRepository userRepository;
	public UserEntity getUserFromRequest(HttpServletRequest request) {
		String token = getJwtFromRequest(request);
		if (StringUtils.hasText(token) && tokenGenerator.validateJwt(token)) {
			String username = tokenGenerator.getUsernameFromJwt(token);
			UserDetails userDetails = customUsersDetailsService.loadUserByUsername(username);
			if (userDetails != null) {
				UserEntity user = userRepository.findUserByUsername(username).orElse(null);
				return user;
			}
		}
		return null;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain) throws ServletException, IOException {
		String token = getJwtFromRequest(request);
		if (StringUtils.hasText(token) && tokenGenerator.validateJwt(token)) {
			String username = tokenGenerator.getUsernameFromJwt(token);

			UserDetails userDetails = customUsersDetailsService.loadUserByUsername(username);
			request.setAttribute("username", username);
			UsernamePasswordAuthenticationToken authenticatonToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
			authenticatonToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authenticatonToken);

			// Add the username to the request attributes
			request.setAttribute("username", username);
		}
		filterChain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
}

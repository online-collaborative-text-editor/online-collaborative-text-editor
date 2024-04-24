package com.APT.online.collaborative.text.editor.Controller;

import com.APT.online.collaborative.text.editor.Model.UserEntity;
import com.APT.online.collaborative.text.editor.Repository.UserRepository;
import com.APT.online.collaborative.text.editor.dto.AuthResponseDto;
import com.APT.online.collaborative.text.editor.dto.LoginDto;
import com.APT.online.collaborative.text.editor.dto.RegisterDto;
import com.APT.online.collaborative.text.editor.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {
	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private JwtGenerator jwtGenerator;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager,
	                      UserRepository userRepository, PasswordEncoder passwordEncoder,
	                      JwtGenerator jwtGenerator) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtGenerator = jwtGenerator;
	}

	@PostMapping("login")
	public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
						loginDto.getPassword())
		);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtGenerator.generateToken(authentication);
		return ResponseEntity.ok(new AuthResponseDto(token));
	}


	@PostMapping("register")
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
		if (userRepository.existsByUsername(registerDto.getUsername())) {
			return ResponseEntity.badRequest().body("Username is already taken");
		}
		UserEntity user = new UserEntity();
		user.setUsername(registerDto.getUsername());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		userRepository.save(user);
		return ResponseEntity.ok("User registered successfully");
	}
}

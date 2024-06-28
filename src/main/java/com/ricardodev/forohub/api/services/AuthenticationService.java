package com.ricardodev.forohub.api.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ricardodev.forohub.api.dtos.LoginUserDto;
import com.ricardodev.forohub.api.dtos.RegisterUserDto;
import com.ricardodev.forohub.api.entities.User;
import com.ricardodev.forohub.api.repositories.UserRepository;

@Service
public class AuthenticationService {
	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final ULIDService ulidService;

	public AuthenticationService(
			UserRepository userRepository,
			AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder,
			ULIDService ulidService) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.ulidService = ulidService;
		this.passwordEncoder = passwordEncoder;
	}

	public User signup(RegisterUserDto input) {
		User user = new User(input);
		user.setId(ulidService.generate());
		user.setPassword(passwordEncoder.encode(input.password()));

		return userRepository.save(user);
	}

	public User authenticate(LoginUserDto input) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						input.email(),
						input.password()));

		return userRepository.findByEmail(input.email())
				.orElseThrow();
	}
}
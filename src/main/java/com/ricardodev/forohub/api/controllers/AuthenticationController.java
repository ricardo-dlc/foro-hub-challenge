package com.ricardodev.forohub.api.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ricardodev.forohub.api.dtos.LoginResponseDto;
import com.ricardodev.forohub.api.dtos.LoginUserDto;
import com.ricardodev.forohub.api.dtos.RegisterUserDto;
import com.ricardodev.forohub.api.dtos.UserReponseDto;
import com.ricardodev.forohub.api.entities.User;
import com.ricardodev.forohub.api.services.AuthenticationService;
import com.ricardodev.forohub.api.services.JWTService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
	private final JWTService jwtService;

	private final AuthenticationService authenticationService;

	public AuthenticationController(JWTService jwtService, AuthenticationService authenticationService) {
		this.jwtService = jwtService;
		this.authenticationService = authenticationService;
	}

	@PostMapping("/signup")
	public ResponseEntity<UserReponseDto> register(@RequestBody RegisterUserDto registerUserDto,
			UriComponentsBuilder uriComponentsBuilder) {
		User registeredUser = authenticationService.signup(registerUserDto);
		UserReponseDto response = new UserReponseDto(registeredUser);
		URI url = uriComponentsBuilder.path("/users/{id}").buildAndExpand(response.id()).toUri();
		return ResponseEntity.created(url).body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginUserDto loginUserDto) {
		User authenticatedUser = authenticationService.authenticate(loginUserDto);

		String jwtToken = jwtService.generateToken(authenticatedUser);
		String expiresAt = jwtService.getExpiresAt(jwtToken);

		LoginResponseDto loginResponse = new LoginResponseDto(jwtToken, expiresAt);

		return ResponseEntity.ok(loginResponse);
	}
}
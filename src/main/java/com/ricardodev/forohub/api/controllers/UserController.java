package com.ricardodev.forohub.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ricardodev.forohub.api.dtos.UserReponseDto;
import com.ricardodev.forohub.api.entities.User;
import com.ricardodev.forohub.api.services.UserService;

@RequestMapping("/users")
@RestController
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/me")
	public ResponseEntity<UserReponseDto> authenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserReponseDto currentUser = new UserReponseDto((User) authentication.getPrincipal());

		return ResponseEntity.ok(currentUser);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserReponseDto> getUserById(@PathVariable String id) {
		var user = userService.getUserById(id);

		return ResponseEntity.ok(user);
	}

	@GetMapping
	public ResponseEntity<Page<UserReponseDto>> allUsers(@PageableDefault(size = 5) Pageable page) {
		var users = userService.allUsers(page);

		return ResponseEntity.ok(users);
	}
}
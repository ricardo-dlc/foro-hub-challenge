package com.ricardodev.forohub.api.dtos;

import java.util.Date;

import com.ricardodev.forohub.api.entities.User;

public record UserReponseDto(String id, String fullName, String email, Date createdAt) {
	public UserReponseDto(User user) {
		this(user.getId(), user.getFullName(), user.getEmail(), user.getCreatedAt());
	}
}

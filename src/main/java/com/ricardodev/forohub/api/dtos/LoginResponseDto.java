package com.ricardodev.forohub.api.dtos;

public record LoginResponseDto(String token, String expiresAt) {
	public LoginResponseDto(String token, String expiresAt) {
		this.token = token;
		this.expiresAt = expiresAt;
	}
}

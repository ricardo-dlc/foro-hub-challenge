package com.ricardodev.forohub.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUserDto(
		@NotBlank @Email String email,
		@NotBlank String password) {
}

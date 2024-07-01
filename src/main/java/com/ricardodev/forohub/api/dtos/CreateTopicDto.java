package com.ricardodev.forohub.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTopicDto(
		@NotBlank @Size(max = 255) String title,
		@NotBlank String message,
		@NotBlank @Size(max = 26, min = 26, message = "Author ID must be exactly 26 characters long") String authorId,
		@NotBlank @Size(max = 26, min = 26, message = "Course ID must be exactly 26 characters long") String courseId) {
}

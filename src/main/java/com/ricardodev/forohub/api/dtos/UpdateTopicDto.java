package com.ricardodev.forohub.api.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ricardodev.forohub.api.entities.BaseEntity.Status;
import com.ricardodev.forohub.validations.StatusDeserializer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateTopicDto(
		@NotBlank @Size(max = 255) String title,
		@NotBlank String message,
		@NotBlank @Size(max = 26, min = 26, message = "Author ID must be exactly 26 characters long") String authorId,
		@NotBlank @Size(max = 26, min = 26, message = "Course ID must be exactly 26 characters long") String courseId,
		@NotNull @JsonDeserialize(using = StatusDeserializer.class) Status status) {

}

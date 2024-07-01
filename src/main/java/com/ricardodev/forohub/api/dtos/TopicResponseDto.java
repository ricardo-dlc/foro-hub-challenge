package com.ricardodev.forohub.api.dtos;

import java.util.Date;

import com.ricardodev.forohub.api.entities.Topic;
import com.ricardodev.forohub.api.entities.BaseEntity.Status;

public record TopicResponseDto(
		String id,
		String title,
		String message,
		String authorId,
		String courseId,
		Status status,
		Date createdAt) {

	public TopicResponseDto(Topic data) {
		this(data.getId(), data.getTitle(), data.getMessage(), data.getAuthor().getId(), data.getCourse().getId(),
				data.getStatus(), data.getCreatedAt());
	}
}

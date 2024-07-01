package com.ricardodev.forohub.api.dtos;

import java.util.Date;

import com.ricardodev.forohub.api.entities.Topic;

public record TopicResponseDto(
		String id,
		String authorId,
		String title,
		String message,
		Date createdAt) {

	public TopicResponseDto(Topic data) {
		this(data.getId(), data.getAuthor().getId(), data.getTitle(), data.getMessage(),
				data.getUpdatedAt());
	}
}

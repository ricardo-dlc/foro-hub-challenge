package com.ricardodev.forohub.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ricardodev.forohub.api.dtos.CreateTopicDto;
import com.ricardodev.forohub.api.dtos.TopicResponseDto;
import com.ricardodev.forohub.api.services.TopicService;

import jakarta.validation.Valid;

@RequestMapping("/topics")
@RestController
public class TopicController {
	private final TopicService topicService;

	public TopicController(TopicService topicService) {
		this.topicService = topicService;
	}

	@PostMapping
	public ResponseEntity<TopicResponseDto> createTopic(@RequestBody @Valid CreateTopicDto data) {
		var topicResponse = topicService.createTopic(data);
		return ResponseEntity.ok(topicResponse);
	}

	@GetMapping
	public ResponseEntity<Page<TopicResponseDto>> getAllTopic(@PageableDefault(size = 5) Pageable page) {
		var topicResponse = topicService.findAll(page);

		return ResponseEntity.ok(topicResponse);
	}
}

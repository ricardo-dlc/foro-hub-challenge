package com.ricardodev.forohub.api.controllers;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ricardodev.forohub.api.dtos.CreateTopicDto;
import com.ricardodev.forohub.api.dtos.TopicResponseDto;
import com.ricardodev.forohub.api.dtos.UpdateTopicDto;
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
	public ResponseEntity<TopicResponseDto> createTopic(@RequestBody @Valid CreateTopicDto data,
			UriComponentsBuilder uriComponentsBuilder) {
		var topicResponse = topicService.createTopic(data);
		URI url = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topicResponse.id()).toUri();
		return ResponseEntity.created(url).body(topicResponse);
	}

	@GetMapping
	public ResponseEntity<Page<TopicResponseDto>> getAllTopics(@PageableDefault(size = 5) Pageable page) {
		var topicResponse = topicService.findAll(page);

		return ResponseEntity.ok(topicResponse);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TopicResponseDto> getTopicById(@PathVariable(required = true) String id) {
		var topicResponse = topicService.findById(id);

		return ResponseEntity.ok(topicResponse);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTopic(@PathVariable(required = true) String id) {
		topicService.deleteTopic(id);

		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateTopic(@PathVariable(required = true) String id,
			@RequestBody @Valid UpdateTopicDto data) {
		topicService.updateTopic(id, data);
		return ResponseEntity.noContent().build();
	}
}

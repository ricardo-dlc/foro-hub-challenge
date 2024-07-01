package com.ricardodev.forohub.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ricardodev.forohub.api.dtos.CreateTopicDto;
import com.ricardodev.forohub.api.dtos.TopicResponseDto;
import com.ricardodev.forohub.api.entities.Topic;
import com.ricardodev.forohub.api.exceptions.DataValidationException;
import com.ricardodev.forohub.api.repositories.CourseRepository;
import com.ricardodev.forohub.api.repositories.TopicRepository;
import com.ricardodev.forohub.api.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TopicService {
	private final TopicRepository topicRepository;
	private final UserRepository userRepository;
	private final CourseRepository courseRepository;

	public TopicService(TopicRepository topicRepository, UserRepository userRepository,
			CourseRepository courseRepository) {
		this.topicRepository = topicRepository;
		this.userRepository = userRepository;
		this.courseRepository = courseRepository;
	}

	@Transactional
	public TopicResponseDto createTopic(CreateTopicDto data) {
		var author = userRepository.findById(data.authorId())
				.orElseThrow(() -> new DataValidationException("Author not found"));
		var course = courseRepository.findById(data.courseId())
				.orElseThrow(() -> new DataValidationException("Course not found"));

		Topic topicData = new Topic(data, author, course);
		Topic savedTopic = topicRepository.saveAndFlush(topicData);

		System.out.println(savedTopic);

		// Convert to DTO within the service
		return new TopicResponseDto(savedTopic);
	}

	public Page<TopicResponseDto> findAll(Pageable page) {
		return topicRepository.findAll(page).map(TopicResponseDto::new);
	}

	public TopicResponseDto findById(String id) {
		var response = topicRepository.getReferenceById(id);
		return new TopicResponseDto(response);
	}

	@Transactional
	public void deleteTopic(String id) {
		var response = topicRepository.getReferenceById(id);

		response.delete();
	}

}

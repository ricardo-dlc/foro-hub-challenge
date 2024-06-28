package com.ricardodev.forohub.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ricardodev.forohub.api.dtos.UserReponseDto;
import com.ricardodev.forohub.api.repositories.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Page<UserReponseDto> allUsers(Pageable page) {
		return userRepository.findAll(page).map(UserReponseDto::new);
	}

	public UserReponseDto getUserById(String id) {
		return new UserReponseDto(userRepository.getReferenceById(id));
	}
}
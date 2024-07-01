package com.ricardodev.forohub.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ricardodev.forohub.api.entities.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, String> {
}

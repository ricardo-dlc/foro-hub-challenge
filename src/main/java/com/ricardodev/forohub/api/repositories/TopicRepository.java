package com.ricardodev.forohub.api.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ricardodev.forohub.api.entities.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, String> {

    Page<Topic> findByDeletedFalse(Pageable page);

    Optional<Topic> findByIdAndDeletedFalse(String id);
}

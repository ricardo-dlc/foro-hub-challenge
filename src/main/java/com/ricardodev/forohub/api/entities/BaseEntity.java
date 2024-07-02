package com.ricardodev.forohub.api.entities;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ricardodev.forohub.utils.UlidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@MappedSuperclass
@Getter
@ToString
@EqualsAndHashCode(of = "id", callSuper = false)
public abstract class BaseEntity {
	public enum Status {
		ACTIVE,
		DELETED
	}

	@Id
	private String id;

	@CreationTimestamp
	@Column(updatable = false, name = "created_at")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(nullable = false)
	private Boolean deleted = false;

	@PrePersist
	public void onCreate() {
		if (id == null) {
			id = UlidGenerator.generate();
		}
	}

	public void delete() {
		deleted = true;
	}

	public void activate() {
		deleted = false;
	}

	public Status getStatus() {
		return deleted ? Status.DELETED : Status.ACTIVE;
	}
}

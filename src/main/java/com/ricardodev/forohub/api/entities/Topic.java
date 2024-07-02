package com.ricardodev.forohub.api.entities;

import com.ricardodev.forohub.api.dtos.CreateTopicDto;
import com.ricardodev.forohub.api.dtos.UpdateTopicDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "topics", uniqueConstraints = { @UniqueConstraint(columnNames = { "title", "message" }) })
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Topic extends BaseEntity {
	@Column(nullable = false, length = 255)
	private String title;

	@Column(nullable = false)
	private String message;

	@ManyToOne
	@JoinColumn(name = "author_id")
	private User author;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	public Topic(CreateTopicDto data, User author, Course course) {
		this.title = data.title();
		this.message = data.message();
		this.author = author;
		this.course = course;
	}

	public void update(UpdateTopicDto data, User author, Course course) {
		this.title = data.title();
		this.message = data.message();
		this.author = author;
		this.course = course;
	}
}

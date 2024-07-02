package com.ricardodev.forohub.api.exceptions;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ErrorResponse {
	private int status;
	private String title;
	private String detail;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String type; // Optional: URI identifying the problem type
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String instance; // Optional: URI identifying the specific occurrence of the problem
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Map<String, Object> properties; // Additional custom properties

	public ErrorResponse() {
		this.properties = new HashMap<>();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status.value();
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public Map<String, Object> getProperties() {
		return this.properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public void setProperty(String key, Object value) {
		this.properties = (this.properties != null ? this.properties : new LinkedHashMap<>());
		this.properties.put(key, value);
	}
}

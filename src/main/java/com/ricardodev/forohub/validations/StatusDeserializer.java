package com.ricardodev.forohub.validations;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.ricardodev.forohub.api.entities.BaseEntity.Status;
import com.ricardodev.forohub.api.exceptions.DataValidationException;

import java.io.IOException;

public class StatusDeserializer extends JsonDeserializer<Status> {
	@Override
	public Status deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		String value = p.getText();
		try {
			return Status.valueOf(value.toUpperCase());
		} catch (IllegalArgumentException e) {

			throw new DataValidationException(
					"Invalid status value: " + value + ". Allowed values are ACTIVE or DELETED.");
		}
	}
}

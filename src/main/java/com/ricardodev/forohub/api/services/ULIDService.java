package com.ricardodev.forohub.api.services;

import org.springframework.stereotype.Service;

import de.huxhorn.sulky.ulid.ULID;

@Service
public class ULIDService {

	private ULID ulid = new ULID();

	public String generate() {
		return ulid.nextULID();
	}
}
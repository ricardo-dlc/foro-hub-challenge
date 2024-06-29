package com.ricardodev.forohub.utils;

import de.huxhorn.sulky.ulid.ULID;

public class UlidGenerator {

	private static ULID ulid = new ULID();

	public static String generate() {
		return ulid.nextULID();
	}
}
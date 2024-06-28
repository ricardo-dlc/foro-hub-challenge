package com.ricardodev.forohub.api.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ricardodev.forohub.api.entities.User;

@Service
public class JWTService {

	@Value("${security.jwt.secret-key}")
	private String secretKey;

	@Value("${security.jwt.expiration-time}")
	private Long expirationTime;

	@Value("${security.jwt.issuer}")
	private String issuer;

	public String generateToken(User userDetails) {
		var payload = new HashMap<String, String>();
		payload.put("id", userDetails.getId());
		payload.put("fullName", userDetails.getFullName());
		payload.put("createAt", userDetails.getCreatedAt().toString());
		return generateToken((UserDetails) userDetails, payload);
	}

	public String generateToken(UserDetails userDetails, Map<String, String> payload) {
		System.out.println("Expiration Time" + expirationTime);
		return buildToken(userDetails, payload, expirationTime);
	}

	public String buildToken(UserDetails userDetails, Map<String, String> payload, Long expiration) {
		Date now = new Date();
		Date expiresAt = new Date(now.getTime() + expiration);
		System.out.println(secretKey);
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		return JWT.create()
				.withIssuer(issuer)
				.withSubject(userDetails.getUsername())
				.withPayload(payload)
				.withExpiresAt(expiresAt)
				.withIssuedAt(now)
				.sign(algorithm);
	}

	public Boolean isTokenValid(String token, UserDetails userDetails) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secretKey);
			JWT.require(algorithm)
					.withIssuer(issuer)
					.withSubject(userDetails.getUsername())
					.build()
					.verify(token);
			return true; // Token is valid and not expired
		} catch (JWTVerificationException | IllegalArgumentException exception) {
			return false; // Token verification failed or expired
		}
	}

	public String getUsername(String token) {
		DecodedJWT decodedJWT = JWT.decode(token);
		return decodedJWT.getSubject();
	}

	public String getExpiresAt(String token) {
		DecodedJWT decodedJWT = JWT.decode(token);
		return decodedJWT.getExpiresAt().toString();
	}
}

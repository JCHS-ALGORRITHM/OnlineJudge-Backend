package com.github.ioloolo.onlinejudge.common.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.github.ioloolo.onlinejudge.domain.user.entity.User;

@Component
public class JwtUtil {

	@Value("${app.security.token-expirations-day}")
	private long tokenExpirationsDay;

	private final Algorithm algorithm;

	public JwtUtil(@Value("${app.security.jwt-secret-key}") String secretKey) {

		this.algorithm = Algorithm.HMAC256(secretKey);
	}

	public String generateAccessToken(Authentication authentication) {

		User userPrincipal = (User) authentication.getPrincipal();

		return JWT.create()
			.withIssuedAt(Instant.now())
			.withExpiresAt(Instant.now().plus(tokenExpirationsDay, ChronoUnit.DAYS))
			.withClaim("username", userPrincipal.getUsername())
			.sign(algorithm);
	}

	public String getUsernameFromAccessToken(String token) {

		return JWT.require(algorithm)
			.build()
			.verify(token)
			.getClaim("username")
			.asString();
	}

	public boolean validate(String token) throws TokenExpiredException {

		if (token == null) return false;

		try {
			JWT.require(algorithm).build().verify(token);

			return true;
		} catch (JWTVerificationException e) {
			return false;
		}
	}
}

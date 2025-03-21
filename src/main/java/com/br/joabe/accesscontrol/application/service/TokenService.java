package com.br.joabe.accesscontrol.application.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.br.joabe.accesscontrol.domain.model.User;

@Service
public class TokenService {

	@Value("${jwt.expiration}")
	private String expiration;

	@Value("${jwt.signing.key}")
	private String signingKey;

	public String generateToken(User user) {
		long exp = Long.parseLong(expiration);
		Instant dataHoraExpiracao = LocalDateTime.now().plusMinutes(exp).toInstant(ZoneOffset.of("-03:00"));

		return JWT
				.create()
				.withIssuer("issuer")
				.withSubject(user.getEmail())
				.withClaim("id", user.getId())
				.withExpiresAt(dataHoraExpiracao)
				.sign(Algorithm.HMAC256(signingKey));
	}

	public String getSubject(String token) {
		return JWT
				.require(Algorithm.HMAC256(signingKey))
				.withIssuer("issuer").build()
				.verify(token)
				.getSubject();
	}

}

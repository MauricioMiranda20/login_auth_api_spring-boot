package com.example.login_auth_api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.login_auth_api.entity.User;

@Service
public class TokenService {
	@Value("${api.security.token.secret}")
	private String secret;
	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.create()
						 		.withIssuer("login-auth-api")
						 		.withSubject(user.getEmail())
						 		.withExpiresAt(generateExpiresDate())
						 		.sign(algorithm);
			
		}catch(JWTCreationException x) { 
			throw new RuntimeException("Error While authenticating");
		}
	}
	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return  JWT.require(algorithm)
					   .withIssuer("login-auth-api")
					   .build()
					   .verify(token)
					   .getSubject();
							  
		}catch(JWTVerificationException x) {return null;}//aqui vai ser null para saber que foi validado
	}
	private Instant generateExpiresDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}

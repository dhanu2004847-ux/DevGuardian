package com.devguardian.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // Loaded from application.properties / env var so the key survives restarts
    // and is shared across instances instead of being random per JVM.
    private final Key key;
    private final long jwtExpiration;
    private final long refreshExpiration;

    public JwtTokenProvider(
            @Value("${devguardian.jwt.secret}") String secret,
            @Value("${devguardian.jwt.expiration-ms:900000}") long jwtExpiration,
            @Value("${devguardian.jwt.refresh-expiration-ms:604800000}") long refreshExpiration) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.jwtExpiration = jwtExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    public String generateAccessToken(String email) {
        return Jwts.builder().setSubject(email).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key, SignatureAlgorithm.HS256).compact();
    }

    public String generateRefreshToken(String email) {
        return Jwts.builder().setSubject(email).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(key, SignatureAlgorithm.HS256).compact();
    }

    public long getRefreshExpirationMs() {
        return refreshExpiration;
    }

    public String validateTokenAndGetEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }
}

package com.example.clean_architecture.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Map;

class TokenFacade {

    private final JwtConfigurationProperties properties;

    TokenFacade(JwtConfigurationProperties properties) {
        this.properties = properties;
    }

    String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    String generateNewToken(UserDetails userDetails) {
        Map<String, Object> claims = Map.of();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + properties.getValidity() * 1000))
                .signWith(key(), SignatureAlgorithm.HS512)
                .compact();
    }

    boolean isValidForUser(String token, UserDetails userDetails) {
        var username = getUsernameFromToken(token);
        return !isTokenExpired(token) && username.equals(userDetails.getUsername());
    }

    private Boolean isTokenExpired(String token) {
        return getAllClaimsFromToken(token)
                .getExpiration()
                .before(new Date());
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(properties.getSecret().getBytes());
    }
}
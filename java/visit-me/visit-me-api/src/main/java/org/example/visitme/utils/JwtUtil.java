package org.example.visitme.utils;

import org.example.visitme.control.dto.LoginDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(LoginDto dto) {
        final long currentTime = System.currentTimeMillis();
        return Jwts.builder()
            .claim("iss", currentTime)
            .claim("sub", dto.getUsername())
            .claim("exp", currentTime + expiration)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }
}

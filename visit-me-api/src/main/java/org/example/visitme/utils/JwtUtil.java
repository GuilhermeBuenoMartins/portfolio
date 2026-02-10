package org.example.visitme.utils;

import java.util.Date;

import org.example.visitme.control.dto.LoginDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
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
            .setIssuedAt(new Date())
            .setSubject(dto.getUsername())
            .setExpiration(new Date(currentTime + expiration))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }

    public boolean isValidToken(String token) {
        final Date now = new Date();
        final String username;
        final Date expiration;
        final Claims claims = getClaims(token);
        if (claims == null) { return false; }
        username= claims.getSubject();
        expiration= claims.getExpiration();
        return username != null && now.before(expiration);
    }

    public String getUsername(String token) {
        final Claims claims = getClaims(token);
        return claims == null? null: claims.getSubject();
    }

    private Claims getClaims(String token) {        
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }
}

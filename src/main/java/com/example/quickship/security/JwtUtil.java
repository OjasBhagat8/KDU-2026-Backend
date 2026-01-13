package com.example.quickship.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.security.KeyRep.Type.SECRET;

@Component
public class JwtUtil {

    private static final long EXPIRATION_TIME = 1000*60*15;
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public Claims parse(String token) {
        return Jwts.parser().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody();
    }
}


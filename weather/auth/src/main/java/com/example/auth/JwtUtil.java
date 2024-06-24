package com.example.auth;

import com.example.auth.entity.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class JwtUtil {

    private static long TTL = 1000 * 60 * 60; // valid for 1 hour
    public final static String AUTH_PREFIX = "Bearer ";

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String generateToken(User user) {
        String username = user.getUsername();
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles());

        return Jwts.builder().setClaims(claims).setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TTL))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes(StandardCharsets.UTF_8)).compact();
    }

    public User parseToken(String token) throws Exception{
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        List<String> roles = claims.get("roles", List.class);
        User u = new User();
        u.setUsername(username);
        u.setRoles(roles);
        return u;
    }
}

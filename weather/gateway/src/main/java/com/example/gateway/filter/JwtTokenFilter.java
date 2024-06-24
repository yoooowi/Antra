package com.example.gateway.filter;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Order(2)
@Slf4j
public class JwtTokenFilter implements GlobalFilter {

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private final String BEARER_PREFIX = "Bearer ";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getPath().toString();
        if (path.equals("/login") || path.startsWith("/auth/")) {
            // don't need to check token for auth server requests
            log.info("Auth server requests. No token needed");
            return chain.filter(exchange);
        }

        String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // request has token
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            String jwtToken = authorizationHeader.substring(BEARER_PREFIX.length());
            Map<String, Object> parsedInfo = parseToken(jwtToken);
            if (parsedInfo == null) {
                // invalid token, reject
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }
            log.info("User: " + parsedInfo.getOrDefault("username", ""));
            log.info("Roles: " + parsedInfo.getOrDefault("roles", "[]"));
            // accept as long as token is valid
            return chain.filter(exchange);

            // TODO compare roles and API to decide accept or deny

        } else {
            log.info("Denied: missing token");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }


    public Map<String, Object> parseToken(String token){
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
            String username = claims.getSubject();
            List<String> roles = claims.get("roles", List.class);
            Map<String, Object> parsedInfo = new HashMap<>();
            parsedInfo.put("username", username);
            parsedInfo.put("roles", roles);
            return parsedInfo;
        } catch (UnsupportedJwtException | SignatureException | MalformedJwtException | ExpiredJwtException | IllegalArgumentException e) {
            log.info("Request has invalid token: " + e.getMessage());
            return null;
        }
    }


}

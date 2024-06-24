package com.example.auth.service.impl;

import com.example.auth.JwtUtil;
import com.example.auth.entity.AuthRequest;
import com.example.auth.entity.User;
import com.example.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String auth(AuthRequest request) {
        log.info("Authorizing: " + request.getUsername());
        Authentication authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(), request.getPassword()
                        )
                );
        User user = (User)authenticate.getPrincipal();
        log.info("Authorized " + request.getUsername());
        return jwtUtil.generateToken(user);
    }

    @Override
    public Map<String, Object> getLoggedInUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> info = new HashMap<>();
        info.put("username", authentication.getName());
        info.put("roles", authentication.getAuthorities());
        return info;
    }
}

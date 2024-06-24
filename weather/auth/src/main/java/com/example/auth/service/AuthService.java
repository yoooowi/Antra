package com.example.auth.service;

import com.example.auth.entity.AuthRequest;

import java.util.Map;

public interface AuthService {

    public String auth(AuthRequest request);

    public Map<String, Object> getLoggedInUserInfo();
}

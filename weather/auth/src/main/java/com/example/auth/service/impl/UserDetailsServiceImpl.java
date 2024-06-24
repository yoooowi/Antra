package com.example.auth.service.impl;

import com.example.auth.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final static User adminUser = new User("admin", "123456", new ArrayList<>());
    private final static User weatherUser = new User("weather", "654321", new ArrayList<>());
    static {
        adminUser.getRoles().add("ROLE_ADMIN");
        adminUser.getRoles().add("ROLE_USER");
        weatherUser.getRoles().add("ROLE_USER");
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (s.equals("admin")) {
            return adminUser;
        } else if (s.equals("weather")) {
            return weatherUser;
        } else {
            throw new UsernameNotFoundException("No username found for: " +s);
        }
    }
}

package com.spendwise.auth.service;

import com.spendwise.auth.dto.*;
import com.spendwise.common.exception.BadRequestException;
import com.spendwise.common.security.JwtService;
import com.spendwise.user.entity.*;
import com.spendwise.user.repository.UserRepository;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager manager;
    private final JwtService jwt;

    public AuthService(UserRepository r, PasswordEncoder e, AuthenticationManager m, JwtService j) {
        repo = r;
        encoder = e;
        manager = m;
        jwt = j;
    }

    public AuthResponse register(RegisterRequest r) {
        if (repo.existsByEmailIgnoreCase(r.email()))
            throw new BadRequestException("Email is already registered");
        User u = new User();
        u.setName(r.name());
        u.setEmail(r.email().toLowerCase());
        u.setPassword(encoder.encode(r.password()));
        u.setRole(Role.USER);
        repo.save(u);
        return response(u, tokenFor(u));
    }

    public AuthResponse login(LoginRequest r) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(r.email(), r.password()));
        User u = repo.findByEmailIgnoreCase(r.email())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));
        return response(u, tokenFor(u));
    }

    private String tokenFor(User u) {
        UserDetails d = org.springframework.security.core.userdetails.User.withUsername(u.getEmail())
                .password(u.getPassword()).roles(u.getRole().name()).build();
        return jwt.generateToken(d);
    }

    private AuthResponse response(User u, String t) {
        return new AuthResponse(t, u.getId(), u.getName(), u.getEmail(), u.getRole().name());
    }
}
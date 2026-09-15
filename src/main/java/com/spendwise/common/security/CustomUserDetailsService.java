package com.spendwise.common.security;

import com.spendwise.user.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repo;

    public CustomUserDetailsService(UserRepository r) {
        repo = r;
    }

    public UserDetails loadUserByUsername(String email) {
        var u = repo.findByEmailIgnoreCase(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User.withUsername(u.getEmail()).password(u.getPassword()).roles(u.getRole().name()).build();
    }
}
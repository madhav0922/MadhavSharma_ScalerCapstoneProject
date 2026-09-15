package com.spendwise.user.service;

import com.spendwise.common.exception.ResourceNotFoundException;
import com.spendwise.user.dto.UserResponse;
import com.spendwise.user.entity.User;
import com.spendwise.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User getByEmail(String email) {
        return repo.findByEmailIgnoreCase(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserResponse profile(String email) {
        return UserResponse.from(getByEmail(email));
    }

    public UserResponse update(String email, String name) {
        User u = getByEmail(email);
        u.setName(name);
        return UserResponse.from(repo.save(u));
    }
}
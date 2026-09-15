package com.spendwise.user.controller;

import com.spendwise.user.dto.UserResponse;
import com.spendwise.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/me")
    public UserResponse me(Authentication a) {
        return service.profile(a.getName());
    }

    @PutMapping("/me")
    public UserResponse update(Authentication a, @RequestParam String name) {
        return service.update(a.getName(), name);
    }
}
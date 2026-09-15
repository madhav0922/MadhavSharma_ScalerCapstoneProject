package com.spendwise.auth.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(@NotBlank String name, @NotBlank @Email String email,
        @NotBlank @Size(min = 8, max = 100) String password) {
}
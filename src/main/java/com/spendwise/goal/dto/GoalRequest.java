package com.spendwise.goal.dto;

import jakarta.validation.constraints.*;
import java.math.*;
import java.time.*;

public record GoalRequest(@NotBlank String name, @NotNull @Positive BigDecimal targetAmount,
        @NotNull @Future LocalDate targetDate) {
}
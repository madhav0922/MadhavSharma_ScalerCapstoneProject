package com.spendwise.budget.dto;

import jakarta.validation.constraints.*;
import java.math.*;

public record BudgetRequest(@NotNull @Positive BigDecimal amount, @Min(1) @Max(12) int month, @Min(2000) int year,
                @NotNull Long categoryId, @Min(1) @Max(100) int alertThreshold) {
}
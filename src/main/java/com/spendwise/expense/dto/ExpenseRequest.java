package com.spendwise.expense.dto;

import jakarta.validation.constraints.*;
import java.math.*;
import java.time.*;

public record ExpenseRequest(@NotNull @Positive BigDecimal amount, @NotBlank String description,
        @NotNull LocalDate expenseDate, @NotBlank String paymentMethod, @NotNull Long categoryId) {
}
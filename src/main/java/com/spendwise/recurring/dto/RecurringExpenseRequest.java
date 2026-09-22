package com.spendwise.recurring.dto;

import jakarta.validation.constraints.*;
import java.math.*;
import java.time.*;

public record RecurringExpenseRequest(@NotNull @Positive BigDecimal amount, @NotBlank String description,
        @Min(1) int intervalDays, @NotNull LocalDate nextRunDate, @NotNull Long categoryId) {
}
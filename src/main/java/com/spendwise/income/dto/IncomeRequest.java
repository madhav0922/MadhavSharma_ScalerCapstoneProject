package com.spendwise.income.dto;

import jakarta.validation.constraints.*;
import java.math.*;
import java.time.*;

public record IncomeRequest(@NotNull @Positive BigDecimal amount, @NotBlank String source,
        @NotNull LocalDate incomeDate) {
}
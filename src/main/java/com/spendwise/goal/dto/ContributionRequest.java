package com.spendwise.goal.dto;

import jakarta.validation.constraints.*;
import java.math.*;

public record ContributionRequest(@NotNull @Positive BigDecimal amount) {
}
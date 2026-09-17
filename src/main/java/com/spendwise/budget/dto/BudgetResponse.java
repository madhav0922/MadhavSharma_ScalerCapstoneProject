package com.spendwise.budget.dto;

import java.math.*;

public record BudgetResponse(Long id, BigDecimal budgetAmount, BigDecimal spentAmount, BigDecimal remainingAmount,
        double utilizationPercent, String category, int month, int year) {
}
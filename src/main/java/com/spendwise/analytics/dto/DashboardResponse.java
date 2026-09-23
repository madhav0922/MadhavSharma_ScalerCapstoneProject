package com.spendwise.analytics.dto;

import java.math.*;
import java.util.*;

public record DashboardResponse(BigDecimal totalIncome, BigDecimal totalExpense, BigDecimal savings, double savingsRate,
        List<CategorySpend> categorySpending) {
    public record CategorySpend(String category, BigDecimal amount) {
    }
}
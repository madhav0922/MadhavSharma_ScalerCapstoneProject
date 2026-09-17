package com.spendwise.expense.dto;

import com.spendwise.expense.entity.Expense;
import java.math.*;
import java.time.*;

public record ExpenseResponse(Long id, BigDecimal amount, String description, LocalDate expenseDate,
        String paymentMethod, Long categoryId, String categoryName) {
    public static ExpenseResponse from(Expense e) {
        return new ExpenseResponse(e.getId(), e.getAmount(), e.getDescription(), e.getExpenseDate(),
                e.getPaymentMethod(), e.getCategory().getId(), e.getCategory().getName());
    }
}
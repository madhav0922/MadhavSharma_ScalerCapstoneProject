package com.spendwise.recurring.dto;

import com.spendwise.recurring.entity.RecurringExpense;
import java.math.*;
import java.time.*;

public record RecurringExpenseResponse(Long id, BigDecimal amount, String description, int intervalDays,
        LocalDate nextRunDate, boolean active, Long categoryId, String categoryName) {
    public static RecurringExpenseResponse from(RecurringExpense r) {
        return new RecurringExpenseResponse(r.getId(), r.getAmount(), r.getDescription(), r.getIntervalDays(),
                r.getNextRunDate(), r.isActive(), r.getCategory().getId(), r.getCategory().getName());
    }
}
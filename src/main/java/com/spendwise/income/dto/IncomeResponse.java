package com.spendwise.income.dto;

import com.spendwise.income.entity.Income;
import java.math.*;
import java.time.*;

public record IncomeResponse(Long id, BigDecimal amount, String source, LocalDate incomeDate) {
    public static IncomeResponse from(Income i) {
        return new IncomeResponse(i.getId(), i.getAmount(), i.getSource(), i.getIncomeDate());
    }
}
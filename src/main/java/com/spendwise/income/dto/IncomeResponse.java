package com.spendwise.income.dto;

import com.spendwise.income.entity.Income;
import java.math.*;

public record IncomeResponse(Long id, BigDecimal amount, String source) {
    public static IncomeResponse from(Income i) {
        return new IncomeResponse(i.getId(), i.getAmount(), i.getSource());
    }
}
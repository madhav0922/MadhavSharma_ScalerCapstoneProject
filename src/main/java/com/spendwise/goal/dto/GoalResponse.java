package com.spendwise.goal.dto;

import com.spendwise.goal.entity.Goal;
import java.math.*;
import java.time.*;

public record GoalResponse(Long id, String name, BigDecimal targetAmount, BigDecimal currentAmount,
        BigDecimal remainingAmount, double progressPercent, LocalDate targetDate) {
    public static GoalResponse from(Goal g) {
        double p = Math.min(100, g.getCurrentAmount().doubleValue() / g.getTargetAmount().doubleValue() * 100);
        return new GoalResponse(g.getId(), g.getName(), g.getTargetAmount(), g.getCurrentAmount(),
                g.getTargetAmount().subtract(g.getCurrentAmount()), p, g.getTargetDate());
    }
}
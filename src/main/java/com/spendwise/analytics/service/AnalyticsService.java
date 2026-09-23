package com.spendwise.analytics.service;

import com.spendwise.analytics.dto.*;
import com.spendwise.expense.repository.ExpenseRepository;
import com.spendwise.income.repository.IncomeRepository;
import com.spendwise.user.service.UserService;
import org.springframework.stereotype.Service;
import java.math.*;
import java.time.*;
import java.util.*;

@Service
public class AnalyticsService {
    final ExpenseRepository expenses;
    final IncomeRepository income;
    final UserService users;

    public AnalyticsService(ExpenseRepository e, IncomeRepository i, UserService u) {
        expenses = e;
        income = i;
        users = u;
    }

    public DashboardResponse dashboard(String email, int month, int year) {
        long id = users.getByEmail(email).getId();
        LocalDate s = LocalDate.of(year, month, 1), en = s.withDayOfMonth(s.lengthOfMonth());
        BigDecimal in = income.sum(id, s, en), out = expenses.sum(id, s, en), save = in.subtract(out);
        double rate = in.signum() == 0 ? 0 : save.doubleValue() / in.doubleValue() * 100;
        List<DashboardResponse.CategorySpend> cs = expenses.categoryTotals(id, s, en).stream()
                .map(x -> new DashboardResponse.CategorySpend((String) x[0], (BigDecimal) x[1])).toList();
        return new DashboardResponse(in, out, save, rate, cs);
    }
}
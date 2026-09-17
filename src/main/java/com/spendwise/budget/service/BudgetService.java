package com.spendwise.budget.service;

import com.spendwise.budget.dto.*;
import com.spendwise.budget.entity.Budget;
import com.spendwise.budget.repository.BudgetRepository;
import com.spendwise.category.service.CategoryService;
import com.spendwise.expense.repository.ExpenseRepository;
import com.spendwise.user.service.UserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BudgetService {

        final BudgetRepository repo;
        final ExpenseRepository expenses;
        final UserService users;
        final CategoryService cats;

        public BudgetService(
                        BudgetRepository r,
                        ExpenseRepository e,
                        UserService u,
                        CategoryService c) {
                repo = r;
                expenses = e;
                users = u;
                cats = c;
        }

        @Transactional(readOnly = true)
        public List<BudgetResponse> list(
                        String email,
                        int month,
                        int year) {

                return repo.findAllByUserIdAndMonthAndYear(
                                users.getByEmail(email).getId(),
                                month,
                                year).stream()
                                .map(this::response)
                                .toList();
        }

        public BudgetResponse create(String email, BudgetRequest r) {

                Budget b = new Budget();

                b.setAmount(r.amount());
                b.setMonth(r.month());
                b.setYear(r.year());
                b.setAlertThreshold(r.alertThreshold());

                b.setCategory(
                                cats.owned(r.categoryId(), email));

                b.setUser(
                                users.getByEmail(email));

                return response(repo.save(b));
        }

        private BudgetResponse response(Budget b) {

                LocalDate s = LocalDate.of(
                                b.getYear(),
                                b.getMonth(),
                                1);

                LocalDate e = s.withDayOfMonth(
                                s.lengthOfMonth());

                BigDecimal spent = expenses.categorySum(
                                b.getUser().getId(),
                                b.getCategory().getId(),
                                s,
                                e);

                double p = spent.doubleValue()
                                / b.getAmount().doubleValue()
                                * 100;

                return new BudgetResponse(
                                b.getId(),
                                b.getAmount(),
                                spent,
                                b.getAmount().subtract(spent),
                                p,
                                b.getCategory().getName(),
                                b.getMonth(),
                                b.getYear());
        }
}
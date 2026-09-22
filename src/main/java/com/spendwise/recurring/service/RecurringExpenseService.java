package com.spendwise.recurring.service;

import com.spendwise.category.service.CategoryService;
import com.spendwise.expense.entity.Expense;
import com.spendwise.expense.repository.ExpenseRepository;
import com.spendwise.recurring.dto.*;
import com.spendwise.recurring.entity.RecurringExpense;
import com.spendwise.recurring.repository.RecurringExpenseRepository;
import com.spendwise.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.*;

@Service
public class RecurringExpenseService {
    final RecurringExpenseRepository recurring;
    final ExpenseRepository expenses;
    final UserService users;
    final CategoryService cats;

    public RecurringExpenseService(RecurringExpenseRepository r, ExpenseRepository e, UserService u,
            CategoryService c) {
        recurring = r;
        expenses = e;
        users = u;
        cats = c;
    }

    public List<RecurringExpenseResponse> list(String e) {
        return recurring.findAllByUserIdOrderByNextRunDateAsc(users.getByEmail(e).getId()).stream()
                .map(RecurringExpenseResponse::from).toList();
    }

    public RecurringExpenseResponse create(String e, RecurringExpenseRequest r) {
        RecurringExpense x = new RecurringExpense();
        x.setAmount(r.amount());
        x.setDescription(r.description());
        x.setIntervalDays(r.intervalDays());
        x.setNextRunDate(r.nextRunDate());
        x.setCategory(cats.owned(r.categoryId(), e));
        x.setUser(users.getByEmail(e));
        return RecurringExpenseResponse.from(recurring.save(x));
    }

    @Transactional
    public void process() {
        for (RecurringExpense r : recurring.findAllByActiveTrueAndNextRunDateLessThanEqual(LocalDate.now())) {
            Expense e = new Expense();
            e.setAmount(r.getAmount());
            e.setDescription(r.getDescription() + " (Recurring)");
            e.setExpenseDate(r.getNextRunDate());
            e.setPaymentMethod("RECURRING");
            e.setCategory(r.getCategory());
            e.setUser(r.getUser());
            expenses.save(e);
            r.setNextRunDate(r.getNextRunDate().plusDays(r.getIntervalDays()));
        }
    }
}
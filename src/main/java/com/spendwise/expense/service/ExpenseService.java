package com.spendwise.expense.service;

import com.spendwise.category.service.CategoryService;
import com.spendwise.common.exception.ResourceNotFoundException;
import com.spendwise.expense.dto.*;
import com.spendwise.expense.entity.Expense;
import com.spendwise.expense.repository.ExpenseRepository;
import com.spendwise.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class ExpenseService {
    final ExpenseRepository repo;
    final UserService users;
    final CategoryService cats;

    public ExpenseService(ExpenseRepository r, UserService u, CategoryService c) {
        repo = r;
        users = u;
        cats = c;
    }

    public List<ExpenseResponse> list(String e) {
        return repo.findAllByUserIdOrderByExpenseDateDesc(users.getByEmail(e).getId()).stream()
                .map(ExpenseResponse::from).toList();
    }

    @Transactional
    public ExpenseResponse create(String e, ExpenseRequest r) {
        var x = new Expense();
        x.setAmount(r.amount());
        x.setDescription(r.description());
        x.setExpenseDate(r.expenseDate());
        x.setPaymentMethod(r.paymentMethod());
        x.setCategory(cats.owned(r.categoryId(), e));
        x.setUser(users.getByEmail(e));
        return ExpenseResponse.from(repo.save(x));
    }

    @Transactional
    public ExpenseResponse update(String e, Long id, ExpenseRequest r) {
        var x = repo.findByIdAndUserId(id, users.getByEmail(e).getId())
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));
        x.setAmount(r.amount());
        x.setDescription(r.description());
        x.setExpenseDate(r.expenseDate());
        x.setPaymentMethod(r.paymentMethod());
        x.setCategory(cats.owned(r.categoryId(), e));
        return ExpenseResponse.from(x);
    }

    public void delete(String e, Long id) {
        var x = repo.findByIdAndUserId(id, users.getByEmail(e).getId())
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));
        repo.delete(x);
    }
}
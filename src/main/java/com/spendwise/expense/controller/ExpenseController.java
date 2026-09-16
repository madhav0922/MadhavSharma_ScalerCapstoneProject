package com.spendwise.expense.controller;

import com.spendwise.expense.dto.*;
import com.spendwise.expense.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    final ExpenseService s;

    public ExpenseController(ExpenseService s) {
        this.s = s;
    }

    @GetMapping
    public List<ExpenseResponse> list(Authentication a) {
        return s.list(a.getName());
    }

    @PostMapping
    public ExpenseResponse create(Authentication a, @Valid @RequestBody ExpenseRequest r) {
        return s.create(a.getName(), r);
    }

    @PutMapping("/{id}")
    public ExpenseResponse update(Authentication a, @PathVariable Long id, @Valid @RequestBody ExpenseRequest r) {
        return s.update(a.getName(), id, r);
    }

    @DeleteMapping("/{id}")
    public void delete(Authentication a, @PathVariable Long id) {
        s.delete(a.getName(), id);
    }
}
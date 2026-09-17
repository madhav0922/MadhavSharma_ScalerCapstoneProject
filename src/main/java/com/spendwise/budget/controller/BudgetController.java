package com.spendwise.budget.controller;

import com.spendwise.budget.dto.*;
import com.spendwise.budget.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.*;
import java.util.*;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {
    final BudgetService s;

    public BudgetController(BudgetService s) {
        this.s = s;
    }

    @GetMapping
    public List<BudgetResponse> list(Authentication a, @RequestParam(defaultValue = "0") int month,
            @RequestParam(defaultValue = "0") int year) {
        var n = LocalDate.now();
        if (month == 0)
            month = n.getMonthValue();
        if (year == 0)
            year = n.getYear();
        return s.list(a.getName(), month, year);
    }

    @PostMapping
    public BudgetResponse create(Authentication a, @Valid @RequestBody BudgetRequest r) {
        return s.create(a.getName(), r);
    }
}
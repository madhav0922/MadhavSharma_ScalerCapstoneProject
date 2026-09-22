package com.spendwise.recurring.controller;

import com.spendwise.recurring.dto.*;
import com.spendwise.recurring.service.RecurringExpenseService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/recurring-expenses")
public class RecurringExpenseController {
    final RecurringExpenseService s;

    public RecurringExpenseController(RecurringExpenseService s) {
        this.s = s;
    }

    @GetMapping
    public List<RecurringExpenseResponse> list(Authentication a) {
        return s.list(a.getName());
    }

    @PostMapping
    public RecurringExpenseResponse create(Authentication a, @Valid @RequestBody RecurringExpenseRequest r) {
        return s.create(a.getName(), r);
    }
}
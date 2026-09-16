package com.spendwise.income.controller;

import com.spendwise.income.dto.*;
import com.spendwise.income.service.IncomeService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/income")
public class IncomeController {
    final IncomeService s;

    public IncomeController(IncomeService s) {
        this.s = s;
    }

    @GetMapping
    public List<IncomeResponse> list(Authentication a) {
        return s.list(a.getName());
    }

    @PostMapping
    public IncomeResponse create(Authentication a, @Valid IncomeRequest r) {
        return s.create(a.getName(), r);
    }

    @PutMapping("/{id}")
    public IncomeResponse update(Authentication a, @PathVariable Long id, @Valid @RequestBody IncomeRequest r) {
        return s.update(a.getName(), id, r);
    }

    @DeleteMapping("/{id}")
    public void delete(Authentication a, @PathVariable Long id) {
        s.delete(a.getName(), id);
    }
}
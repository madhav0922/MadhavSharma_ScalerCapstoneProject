package com.spendwise.goal.controller;

import com.spendwise.goal.dto.*;
import com.spendwise.goal.service.GoalService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/goals")
public class GoalController {
    final GoalService s;

    public GoalController(GoalService s) {
        this.s = s;
    }

    @GetMapping
    public List<GoalResponse> list(Authentication a) {
        return s.list(a.getName());
    }

    @PostMapping
    public GoalResponse create(Authentication a, @Valid GoalRequest r) {
        return s.create(a.getName(), r);
    }

    @PostMapping("/{id}/contributions")
    public GoalResponse contribute(Authentication a, @PathVariable Long id, @RequestBody ContributionRequest r) {
        return s.contribute(a.getName(), id, r);
    }
}
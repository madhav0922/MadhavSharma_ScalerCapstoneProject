package com.spendwise.goal.service;

import com.spendwise.common.exception.ResourceNotFoundException;
import com.spendwise.goal.dto.*;
import com.spendwise.goal.entity.*;
import com.spendwise.goal.repository.*;
import com.spendwise.user.service.UserService;
import org.springframework.stereotype.Service;
import java.time.*;
import java.math.*;
import java.util.*;

@Service
public class GoalService {
    final GoalRepository goals;
    final GoalContributionRepository contributions;
    final UserService users;

    public GoalService(GoalRepository g, GoalContributionRepository c, UserService u) {
        goals = g;
        contributions = c;
        users = u;
    }

    public List<GoalResponse> list(String e) {
        return goals.findAllByUserIdOrderByTargetDateAsc(users.getByEmail(e).getId()).stream().map(GoalResponse::from)
                .toList();
    }

    public GoalResponse create(String e, GoalRequest r) {
        Goal g = new Goal();
        g.setName(r.name());
        g.setTargetAmount(r.targetAmount());
        g.setCurrentAmount(BigDecimal.ZERO);
        g.setUser(users.getByEmail(e));
        return GoalResponse.from(goals.save(g));
    }

    public GoalResponse contribute(String e, Long id, ContributionRequest r) {
        Goal g = goals.findByIdAndUserId(id, users.getByEmail(e).getId())
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));
        GoalContribution c = new GoalContribution();
        c.setGoal(g);
        c.setAmount(r.amount());
        c.setContributionDate(LocalDate.now());
        contributions.save(c);
        g.setCurrentAmount(g.getCurrentAmount().add(r.amount()));
        return GoalResponse.from(g);
    }
}
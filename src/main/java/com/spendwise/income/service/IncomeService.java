package com.spendwise.income.service;

import com.spendwise.common.exception.ResourceNotFoundException;
import com.spendwise.income.dto.*;
import com.spendwise.income.entity.Income;
import com.spendwise.income.repository.IncomeRepository;
import com.spendwise.user.service.UserService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class IncomeService {
    final IncomeRepository repo;
    final UserService users;

    public IncomeService(IncomeRepository r, UserService u) {
        repo = r;
        users = u;
    }

    public List<IncomeResponse> list(String e) {
        return repo.findAllByUserIdOrderByIncomeDateDesc(users.getByEmail(e).getId()).stream().map(IncomeResponse::from)
                .toList();
    }

    public IncomeResponse create(String e, IncomeRequest r) {
        var i = new Income();
        i.setAmount(r.amount());
        i.setSource(r.source());
        i.setIncomeDate(r.incomeDate());
        i.setUser(users.getByEmail(e));
        return IncomeResponse.from(repo.save(i));
    }

    public IncomeResponse update(String e, Long id, IncomeRequest r) {
        var i = repo.findByIdAndUserId(id, users.getByEmail(e).getId())
                .orElseThrow(() -> new ResourceNotFoundException("Income not found"));
        i.setAmount(r.amount());
        i.setSource(r.source());
        i.setIncomeDate(r.incomeDate());
        return IncomeResponse.from(i);
    }

    public void delete(String e, Long id) {
        repo.delete(repo.findByIdAndUserId(id, users.getByEmail(e).getId())
                .orElseThrow(() -> new ResourceNotFoundException("Income not found")));
    }
}
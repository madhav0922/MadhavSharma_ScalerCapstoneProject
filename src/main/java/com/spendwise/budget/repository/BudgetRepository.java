package com.spendwise.budget.repository;

import com.spendwise.budget.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findAllByUserIdAndMonthAndYear(Long id, int month, int year);
}
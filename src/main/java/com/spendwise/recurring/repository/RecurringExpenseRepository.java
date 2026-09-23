package com.spendwise.recurring.repository;

import com.spendwise.recurring.entity.RecurringExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.*;
import java.util.*;

public interface RecurringExpenseRepository extends JpaRepository<RecurringExpense, Long> {
    List<RecurringExpense> findAllByUserIdOrderByNextRunDateAsc(Long id);

    List<RecurringExpense> findAllByActiveTrueAndNextRunDateLessThanEqual(LocalDate d);
}
package com.spendwise.expense.repository;

import com.spendwise.expense.entity.Expense;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.math.*;
import java.time.*;
import java.util.*;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByUserIdOrderByExpenseDateDesc(Long id);

    Optional<Expense> findByIdAndUserId(Long id, Long userId);

    @Query("select coalesce(sum(e.amount),0) from Expense e where e.user.id=:u and e.expenseDate between :s and :en")
    BigDecimal sum(@Param("u") Long u, @Param("s") LocalDate s, @Param("en") LocalDate en);

    @Query("select coalesce(sum(e.amount),0) from Expense e where e.user.id=:u and e.category.id=:c and e.expenseDate between :s and :en")
    BigDecimal categorySum(@Param("u") Long u, @Param("c") Long c, @Param("s") LocalDate s, @Param("en") LocalDate en);

    @Query("select e.category.name,coalesce(sum(e.amount),0) from Expense e where e.user.id=:u and e.expenseDate between :s and :en group by e.category.name order by sum(e.amount) desc")
    List<Object[]> categoryTotals(@Param("u") Long u, @Param("s") LocalDate s, @Param("en") LocalDate en);
}
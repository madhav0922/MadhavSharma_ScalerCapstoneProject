package com.spendwise.income.repository;

import com.spendwise.income.entity.Income;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.math.*;
import java.time.*;
import java.util.*;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findAllByUserIdOrderByIncomeDateDesc(Long id);

    Optional<Income> findByIdAndUserId(Long id, Long userId);

    @Query("select coalesce(sum(i.amount),0) from Income i where i.user.id=:u and i.incomeDate between :s and :en")
    BigDecimal sum(@Param("u") Long u, @Param("s") LocalDate s, @Param("en") LocalDate en);
}
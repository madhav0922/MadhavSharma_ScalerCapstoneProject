package com.spendwise.goal.repository;

import com.spendwise.goal.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findAllByUserIdOrderByTargetDateAsc(Long id);

    Optional<Goal> findByIdAndUserId(Long id, Long userId);
}
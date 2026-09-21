package com.spendwise.goal.repository;

import com.spendwise.goal.entity.GoalContribution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalContributionRepository extends JpaRepository<GoalContribution, Long> {
}
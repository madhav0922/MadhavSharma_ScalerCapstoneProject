package com.spendwise.goal.entity;

import jakarta.persistence.*;
import java.math.*;
import java.time.*;

@Entity
@Table(name = "goal_contributions")
public class GoalContribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id")
    Goal goal;
    @Column(nullable = false)
    BigDecimal amount;
    @Column(nullable = false)
    LocalDate contributionDate;

    public void setGoal(Goal v) {
        goal = v;
    }

    public void setAmount(BigDecimal v) {
        amount = v;
    }

    public void setContributionDate(LocalDate v) {
        contributionDate = v;
    }
}
package com.spendwise.goal.entity;

import com.spendwise.user.entity.User;
import jakarta.persistence.*;
import java.math.*;
import java.time.*;

@Entity
@Table(name = "goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    BigDecimal targetAmount;
    @Column(nullable = false)
    BigDecimal currentAmount = BigDecimal.ZERO;
    @Column(nullable = false)
    LocalDate targetDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setName(String v) {
        name = v;
    }

    public void setTargetAmount(BigDecimal v) {
        targetAmount = v;
    }

    public void setCurrentAmount(BigDecimal v) {
        currentAmount = v;
    }

    public void setTargetDate(LocalDate v) {
        targetDate = v;
    }

    public void setUser(User v) {
        user = v;
    }
}
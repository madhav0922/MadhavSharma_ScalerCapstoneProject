package com.spendwise.recurring.entity;

import com.spendwise.category.entity.Category;
import com.spendwise.user.entity.User;
import jakarta.persistence.*;
import java.math.*;
import java.time.*;

@Entity
@Table(name = "recurring_expenses")
public class RecurringExpense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    BigDecimal amount;
    @Column(nullable = false)
    String description;
    @Column(nullable = false)
    int intervalDays;
    @Column(nullable = false)
    LocalDate nextRunDate;
    @Column(nullable = false)
    boolean active = true;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public int getIntervalDays() {
        return intervalDays;
    }

    public LocalDate getNextRunDate() {
        return nextRunDate;
    }

    public boolean isActive() {
        return active;
    }

    public Category getCategory() {
        return category;
    }

    public User getUser() {
        return user;
    }

    public void setAmount(BigDecimal v) {
        amount = v;
    }

    public void setDescription(String v) {
        description = v;
    }

    public void setIntervalDays(int v) {
        intervalDays = v;
    }

    public void setNextRunDate(LocalDate v) {
        nextRunDate = v;
    }

    public void setCategory(Category v) {
        category = v;
    }

    public void setUser(User v) {
        user = v;
    }
}
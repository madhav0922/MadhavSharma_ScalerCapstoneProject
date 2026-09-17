package com.spendwise.budget.entity;

import com.spendwise.category.entity.Category;
import com.spendwise.user.entity.User;
import jakarta.persistence.*;
import java.math.*;

@Entity
@Table(name = "budgets")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    BigDecimal amount;
    @Column(nullable = false)
    int month;
    @Column(nullable = false)
    int year;
    @Column(nullable = false)
    int alertThreshold = 80;
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

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getAlertThreshold() {
        return alertThreshold;
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

    public void setMonth(int v) {
        month = v;
    }

    public void setYear(int v) {
        year = v;
    }

    public void setAlertThreshold(int v) {
        alertThreshold = v;
    }

    public void setCategory(Category v) {
        category = v;
    }

    public void setUser(User v) {
        user = v;
    }
}
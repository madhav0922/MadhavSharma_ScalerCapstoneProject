package com.spendwise.expense.entity;

import com.spendwise.category.entity.Category;
import com.spendwise.user.entity.User;
import jakarta.persistence.*;
import java.math.*;
import java.time.*;

@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    BigDecimal amount;
    @Column(nullable = false)
    String description;
    @Column(nullable = false)
    LocalDate expenseDate;
    @Column(nullable = false)
    String paymentMethod;
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
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

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Category getCategory() {
        return category;
    }

    public void setAmount(BigDecimal v) {
        amount = v;
    }

    public void setDescription(String v) {
        description = v;
    }

    public void setExpenseDate(LocalDate v) {
        expenseDate = v;
    }

    public void setPaymentMethod(String v) {
        paymentMethod = v;
    }

    public void setCategory(Category v) {
        category = v;
    }

    public void setUser(User v) {
        user = v;
    }
}
package com.spendwise.income.entity;

import com.spendwise.user.entity.User;
import jakarta.persistence.*;
import java.math.*;
import java.time.*;

@Entity
@Table(name = "income")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    BigDecimal amount;
    @Column(nullable = false)
    String source;
    @Column(nullable = false)
    LocalDate incomeDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getSource() {
        return source;
    }

    public LocalDate getIncomeDate() {
        return incomeDate;
    }

    public void setAmount(BigDecimal v) {
        amount = v;
    }

    public void setSource(String v) {
        source = v;
    }

    public void setIncomeDate(LocalDate v) {
        incomeDate = v;
    }

    public void setUser(User v) {
        user = v;
    }
}
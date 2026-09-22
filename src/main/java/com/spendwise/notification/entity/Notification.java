package com.spendwise.notification.entity;

import com.spendwise.user.entity.User;
import jakarta.persistence.*;
import java.time.*;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String title;
    @Column(nullable = false)
    String message;
    @Column(nullable = false)
    boolean readFlag = false;
    @Column(nullable = false)
    LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @PrePersist
    void pre() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public boolean isReadFlag() {
        return readFlag;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setTitle(String v) {
        title = v;
    }

    public void setMessage(String v) {
        message = v;
    }

    public void setReadFlag(boolean v) {
        readFlag = v;
    }

    public void setUser(User v) {
        user = v;
    }
}
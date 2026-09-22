package com.spendwise.notification.service;

import com.spendwise.notification.dto.*;
import com.spendwise.notification.entity.Notification;
import com.spendwise.notification.repository.NotificationRepository;
import com.spendwise.user.service.UserService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class NotificationService {
    final NotificationRepository repo;
    final UserService users;

    public NotificationService(NotificationRepository r, UserService u) {
        repo = r;
        users = u;
    }

    public List<NotificationResponse> list(String e) {
        return repo.findAllByUserIdOrderByCreatedAtDesc(users.getByEmail(e).getId()).stream()
                .map(NotificationResponse::from).toList();
    }

    public void markRead(String e, Long id) {
        repo.findByIdAndUserId(id, users.getByEmail(e).getId()).ifPresent(n -> n.setReadFlag(true));
    }

    public void create(String e, String title, String message) {
        Notification n = new Notification();
        n.setUser(users.getByEmail(e));
        n.setTitle(title);
        n.setMessage(message);
        repo.save(n);
    }
}
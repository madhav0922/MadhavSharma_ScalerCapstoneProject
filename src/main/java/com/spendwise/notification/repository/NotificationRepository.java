package com.spendwise.notification.repository;

import com.spendwise.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUserIdOrderByCreatedAtDesc(Long id);

    Optional<Notification> findByIdAndUserId(Long id, Long userId);
}
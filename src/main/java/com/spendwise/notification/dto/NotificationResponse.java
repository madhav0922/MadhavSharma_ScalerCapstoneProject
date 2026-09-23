package com.spendwise.notification.dto;

import com.spendwise.notification.entity.Notification;
import java.time.*;

public record NotificationResponse(Long id, String title, String message, boolean read, LocalDateTime createdAt) {
    public static NotificationResponse from(Notification n) {
        return new NotificationResponse(n.getId(), n.getTitle(), n.getMessage(), n.isReadFlag(), n.getCreatedAt());
    }
}
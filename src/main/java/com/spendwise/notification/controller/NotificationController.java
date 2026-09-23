package com.spendwise.notification.controller;

import com.spendwise.notification.dto.*;
import com.spendwise.notification.service.NotificationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    final NotificationService s;

    public NotificationController(NotificationService s) {
        this.s = s;
    }

    @GetMapping
    public List<NotificationResponse> list(Authentication a) {
        return s.list(a.getName());
    }

    @PatchMapping("/{id}/read")
    public void read(Authentication a, @PathVariable Long id) {
        s.markRead(a.getName(), id);
    }
}
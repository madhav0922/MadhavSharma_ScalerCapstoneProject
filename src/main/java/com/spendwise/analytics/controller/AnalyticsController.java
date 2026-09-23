package com.spendwise.analytics.controller;

import com.spendwise.analytics.dto.*;
import com.spendwise.analytics.service.AnalyticsService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.*;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    final AnalyticsService s;

    public AnalyticsController(AnalyticsService s) {
        this.s = s;
    }

    @GetMapping("/dashboard")
    public DashboardResponse dashboard(Authentication a, @RequestParam(defaultValue = "0") int month,
            @RequestParam(defaultValue = "0") int year) {
        var n = LocalDate.now();
        if (month == 0)
            month = n.getMonthValue();
        if (year == 0)
            year = n.getYear();
        return s.dashboard(a.getName(), month, year);
    }
}
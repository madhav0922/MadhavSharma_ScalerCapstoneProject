package com.spendwise.recurring.scheduler;

import com.spendwise.recurring.service.RecurringExpenseService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RecurringExpenseScheduler {
    final RecurringExpenseService s;

    public RecurringExpenseScheduler(RecurringExpenseService s) {
        this.s = s;
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void run() {
        s.process();
    }
}
package com.spendwise;

import com.spendwise.budget.dto.BudgetResponse;
import com.spendwise.budget.entity.Budget;
import com.spendwise.budget.repository.BudgetRepository;
import com.spendwise.budget.service.BudgetService;
import com.spendwise.category.entity.Category;
import com.spendwise.category.service.CategoryService;
import com.spendwise.expense.repository.ExpenseRepository;
import com.spendwise.user.entity.User;
import com.spendwise.user.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpendWiseApplicationTests {

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private UserService userService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private User user;

    @Mock
    private Category category;

    @Mock
    private Budget budget;

    @InjectMocks
    private BudgetService budgetService;

    @BeforeEach
    void setUp() {

        // Mock generated IDs instead of using setId()
        when(user.getId()).thenReturn(1L);

        when(category.getId()).thenReturn(1L);
        when(category.getName()).thenReturn("Food");

        when(budget.getId()).thenReturn(1L);
        when(budget.getUser()).thenReturn(user);
        when(budget.getCategory()).thenReturn(category);

        when(budget.getAmount())
                .thenReturn(new BigDecimal("5000"));

        when(budget.getMonth())
                .thenReturn(9);

        when(budget.getYear())
                .thenReturn(2026);

        when(budget.getAlertThreshold())
                .thenReturn(80);
    }

    @Test
    void shouldCreateBudgetService() {

        assertNotNull(budgetService);
    }

    @Test
    void shouldReturnBudgetsForUser() {

        when(userService.getByEmail("test@example.com"))
                .thenReturn(user);

        when(
                budgetRepository.findAllByUserIdAndMonthAndYear(
                        1L,
                        9,
                        2026))
                .thenReturn(List.of(budget));

        when(
                expenseRepository.categorySum(
                        eq(1L),
                        eq(1L),
                        any(LocalDate.class),
                        any(LocalDate.class)))
                .thenReturn(new BigDecimal("2000"));

        List<BudgetResponse> result = budgetService.list(
                "test@example.com",
                9,
                2026);

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(userService)
                .getByEmail("test@example.com");

        verify(budgetRepository)
                .findAllByUserIdAndMonthAndYear(
                        1L,
                        9,
                        2026);

        verify(expenseRepository)
                .categorySum(
                        eq(1L),
                        eq(1L),
                        any(LocalDate.class),
                        any(LocalDate.class));
    }

    @Test
    void shouldCalculateBudgetUtilizationCorrectly() {

        when(userService.getByEmail("test@example.com"))
                .thenReturn(user);

        when(
                budgetRepository.findAllByUserIdAndMonthAndYear(
                        1L,
                        9,
                        2026))
                .thenReturn(List.of(budget));

        when(
                expenseRepository.categorySum(
                        eq(1L),
                        eq(1L),
                        any(LocalDate.class),
                        any(LocalDate.class)))
                .thenReturn(new BigDecimal("2000"));

        List<BudgetResponse> result = budgetService.list(
                "test@example.com",
                9,
                2026);

        assertEquals(1, result.size());

        BudgetResponse response = result.get(0);

        assertEquals(
                new BigDecimal("5000"),
                response.budgetAmount());

        assertEquals(
                new BigDecimal("2000"),
                response.spentAmount());

        assertEquals(
                new BigDecimal("3000"),
                response.remainingAmount());

        assertEquals(
                40.0,
                response.utilizationPercent());

        assertEquals(
                "Food",
                response.category());
    }

    @Test
    void shouldReturnEmptyListWhenNoBudgetsExist() {

        when(userService.getByEmail("test@example.com"))
                .thenReturn(user);

        when(
                budgetRepository.findAllByUserIdAndMonthAndYear(
                        1L,
                        9,
                        2026))
                .thenReturn(List.of());

        List<BudgetResponse> result = budgetService.list(
                "test@example.com",
                9,
                2026);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(expenseRepository, never())
                .categorySum(
                        anyLong(),
                        anyLong(),
                        any(LocalDate.class),
                        any(LocalDate.class));
    }

    @Test
    void shouldCalculateHundredPercentUtilization() {

        when(userService.getByEmail("test@example.com"))
                .thenReturn(user);

        when(
                budgetRepository.findAllByUserIdAndMonthAndYear(
                        1L,
                        9,
                        2026))
                .thenReturn(List.of(budget));

        when(
                expenseRepository.categorySum(
                        eq(1L),
                        eq(1L),
                        any(LocalDate.class),
                        any(LocalDate.class)))
                .thenReturn(new BigDecimal("5000"));

        List<BudgetResponse> result = budgetService.list(
                "test@example.com",
                9,
                2026);

        BudgetResponse response = result.get(0);

        assertEquals(
                100.0,
                response.utilizationPercent());

        assertEquals(
                BigDecimal.ZERO,
                response.remainingAmount());
    }

    @Test
    void shouldHandleZeroSpentAmount() {

        when(userService.getByEmail("test@example.com"))
                .thenReturn(user);

        when(
                budgetRepository.findAllByUserIdAndMonthAndYear(
                        1L,
                        9,
                        2026))
                .thenReturn(List.of(budget));

        when(
                expenseRepository.categorySum(
                        eq(1L),
                        eq(1L),
                        any(LocalDate.class),
                        any(LocalDate.class)))
                .thenReturn(BigDecimal.ZERO);

        List<BudgetResponse> result = budgetService.list(
                "test@example.com",
                9,
                2026);

        BudgetResponse response = result.get(0);

        assertEquals(
                BigDecimal.ZERO,
                response.spentAmount());

        assertEquals(
                new BigDecimal("5000"),
                response.remainingAmount());

        assertEquals(
                0.0,
                response.utilizationPercent());
    }

    @Test
    void shouldVerifyCorrectDateRangeForSeptember() {

        when(userService.getByEmail("test@example.com"))
                .thenReturn(user);

        when(
                budgetRepository.findAllByUserIdAndMonthAndYear(
                        1L,
                        9,
                        2026))
                .thenReturn(List.of(budget));

        when(
                expenseRepository.categorySum(
                        anyLong(),
                        anyLong(),
                        any(LocalDate.class),
                        any(LocalDate.class)))
                .thenReturn(new BigDecimal("1000"));

        budgetService.list(
                "test@example.com",
                9,
                2026);

        verify(expenseRepository).categorySum(
                eq(1L),
                eq(1L),
                eq(LocalDate.of(2026, 9, 1)),
                eq(LocalDate.of(2026, 9, 30)));
    }

    @Test
    void shouldNotQueryExpensesWhenThereAreNoBudgets() {

        when(userService.getByEmail("test@example.com"))
                .thenReturn(user);

        when(
                budgetRepository.findAllByUserIdAndMonthAndYear(
                        1L,
                        9,
                        2026))
                .thenReturn(List.of());

        budgetService.list(
                "test@example.com",
                9,
                2026);

        verify(expenseRepository, never())
                .categorySum(
                        anyLong(),
                        anyLong(),
                        any(LocalDate.class),
                        any(LocalDate.class));
    }
}
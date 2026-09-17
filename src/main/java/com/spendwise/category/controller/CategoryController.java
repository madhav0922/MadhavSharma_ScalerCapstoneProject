package com.spendwise.category.controller;

import com.spendwise.category.dto.*;
import com.spendwise.category.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    final CategoryService s;

    public CategoryController(CategoryService s) {
        this.s = s;
    }

    @GetMapping
    public List<CategoryResponse> list(Authentication a) {
        return s.list(a.getName());
    }

    @PostMapping
    public CategoryResponse create(Authentication a, @Valid @RequestBody CategoryRequest r) {
        return s.create(a.getName(), r);
    }
}
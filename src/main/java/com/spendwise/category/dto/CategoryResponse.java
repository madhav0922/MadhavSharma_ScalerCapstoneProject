package com.spendwise.category.dto;

import com.spendwise.category.entity.Category;

public record CategoryResponse(Long id, String name) {
    public static CategoryResponse from(Category c) {
        return new CategoryResponse(c.getId(), c.getName());
    }
}
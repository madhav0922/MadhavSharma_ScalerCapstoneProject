package com.spendwise.category.service;

import com.spendwise.category.dto.*;
import com.spendwise.category.entity.Category;
import com.spendwise.category.repository.CategoryRepository;
import com.spendwise.common.exception.*;
import com.spendwise.user.service.UserService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CategoryService {
    final CategoryRepository repo;
    final UserService users;

    public CategoryService(CategoryRepository r, UserService u) {
        repo = r;
        users = u;
    }

    public List<CategoryResponse> list(String e) {
        long id = users.getByEmail(e).getId();
        return repo.findAllByUserIdOrderByName(id).stream().map(CategoryResponse::from).toList();
    }

    public CategoryResponse create(String e, CategoryRequest r) {
        var u = users.getByEmail(e);
        if (repo.existsByNameIgnoreCaseAndUserId(r.name(), u.getId()))
            throw new BadRequestException("Category already exists");
        Category c = new Category();
        c.setName(r.name());
        c.setUser(u);
        return CategoryResponse.from(repo.save(c));
    }

    public Category owned(Long id, String e) {
        return repo.findByIdAndUserId(id, users.getByEmail(e).getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }
}
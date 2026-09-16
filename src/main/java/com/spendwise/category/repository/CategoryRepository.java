package com.spendwise.category.repository;

import com.spendwise.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByUserIdOrderByName(Long id);

    Optional<Category> findByIdAndUserId(Long id, Long userId);

    boolean existsByNameIgnoreCaseAndUserId(String name, Long userId);
}
package com.example.project.repository.jpa.repository.category;

import com.example.project.repository.jpa.entity.category.CategoryEntity;
import com.example.project.repository.jpa.repository.AbstractJpaBaseTimeEntityRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;

class CategoryJpaRepositoryTest extends AbstractJpaBaseTimeEntityRepositoryTest<CategoryEntity, Long> {
    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    @Override
    protected CategoryJpaRepository repository() {
        return categoryJpaRepository;
    }

    @Override
    protected CategoryEntity createTestInstance() {
        return new CategoryEntity("category1");
    }
}

package com.example.project.repository.jpa.repository.category.impl;

import static com.example.project.repository.jpa.entity.category.QCategoryEntity.categoryEntity;

import com.example.project.repository.jpa.repository.AbstractQueryDslRepository;
import com.example.project.repository.jpa.repository.category.CustomCategoryJpaRepository;
import jakarta.persistence.EntityManager;
import java.util.List;

public class CategoryJpaRepositoryImpl extends AbstractQueryDslRepository implements
    CustomCategoryJpaRepository {

    public CategoryJpaRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<String> findAllCategoryNames() {
        return queryFactory.select(categoryEntity.name)
            .from(categoryEntity)
            .fetch();
    }
}

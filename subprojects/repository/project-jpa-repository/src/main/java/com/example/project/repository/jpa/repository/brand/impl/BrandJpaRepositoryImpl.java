package com.example.project.repository.jpa.repository.brand.impl;

import static com.example.project.repository.jpa.entity.brand.QBrandEntity.brandEntity;

import com.example.project.repository.jpa.repository.AbstractQueryDslRepository;
import com.example.project.repository.jpa.repository.brand.CustomBrandJpaRepository;
import jakarta.persistence.EntityManager;
import java.util.List;

public class BrandJpaRepositoryImpl extends AbstractQueryDslRepository implements CustomBrandJpaRepository {

    public BrandJpaRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<String> findAllBrandNames() {
        return queryFactory
            .select(brandEntity.name)
            .from(brandEntity)
            .fetch();
    }
}

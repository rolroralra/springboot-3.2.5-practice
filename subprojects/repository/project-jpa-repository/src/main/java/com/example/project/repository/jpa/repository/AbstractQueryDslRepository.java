package com.example.project.repository.jpa.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

public abstract class AbstractQueryDslRepository {
    protected final JPAQueryFactory queryFactory;

    protected AbstractQueryDslRepository(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }
}

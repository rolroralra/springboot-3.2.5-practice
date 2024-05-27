package com.example.project.repository.jpa.repository.item.impl;


import static com.example.project.repository.jpa.entity.brand.QBrandEntity.brandEntity;
import static com.example.project.repository.jpa.entity.category.QCategoryEntity.categoryEntity;
import static com.example.project.repository.jpa.entity.item.QItemEntity.itemEntity;

import com.example.project.repository.jpa.entity.item.QItemEntity;
import com.example.project.repository.jpa.repository.AbstractQueryDslRepository;
import com.example.project.repository.jpa.repository.item.CustomItemJpaRepository;
import com.example.project.repository.jpa.repository.item.model.BrandPriceDto;
import com.example.project.repository.jpa.repository.item.model.CategoryBrandMinPriceDto;
import com.example.project.repository.jpa.repository.item.model.CategoryMinMaxPriceDto;
import com.example.project.repository.jpa.repository.item.model.CategoryMinPriceSummationDto;
import com.example.project.repository.jpa.repository.item.model.CategoryPriceDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import jakarta.persistence.EntityManager;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ItemJpaRepositoryImpl extends AbstractQueryDslRepository implements CustomItemJpaRepository {

    public ItemJpaRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public CategoryMinPriceSummationDto findMinPriceGroupByCategoryId() {
        QItemEntity subItemEntity = new QItemEntity("subItemEntity");

        // 서브쿼리: 각 카테고리별 최저 가격
        List<Tuple> minPriceResults = queryFactory
            .select(
                itemEntity.category.id,
                itemEntity.category.name,
                itemEntity.brand.name,
                itemEntity.price
            )
            .from(itemEntity)
            .where(
                itemEntity.price.eq(
                    JPAExpressions
                        .select(subItemEntity.price.min())
                        .from(subItemEntity)
                        .where(subItemEntity.category.id.eq(itemEntity.category.id))
                )
            )
            .orderBy(itemEntity.category.id.asc())
            .fetch();

        // 카테고리별 최저가 브랜드와 가격 조회
        List<CategoryBrandMinPriceDto> categoryBrandMinPriceDtos = minPriceResults.stream()
            .collect(
                Collectors.toMap(
                    tuple -> tuple.get(itemEntity.category.name),
                    tuple -> new BrandPriceDto(
                        tuple.get(itemEntity.brand.name),
                        tuple.get(itemEntity.price)
                    ),
                    (a, b) -> b,            // 중복된 키가 있을 경우 덮어씀
                    LinkedHashMap::new
                )
            )
            .entrySet().stream()
            .map(entry -> new CategoryBrandMinPriceDto(entry.getKey(), List.of(entry.getValue())))
            .toList();

        Long totalMinPrice = categoryBrandMinPriceDtos.stream().map(CategoryBrandMinPriceDto::minBrandPrice)
            .map(List::getFirst)
            .mapToLong(BrandPriceDto::price)
            .sum();

        return new CategoryMinPriceSummationDto(categoryBrandMinPriceDtos, totalMinPrice);
    }

    @Override
    public List<CategoryPriceDto> findMinPriceGroupByCategoryNameWhereBrandIdEq(Long brandId) {
        return queryFactory
            .select(
                Projections.constructor(
                    CategoryPriceDto.class,
                    categoryEntity.name,
                    itemEntity.price.min()))
            .from(itemEntity)
            .join(itemEntity.category, categoryEntity)
            .where(itemEntity.brand.id.eq(brandId))
            .groupBy(itemEntity.category.id)
            .orderBy(itemEntity.category.id.asc())
            .fetch();
    }

    @Override
    public CategoryMinMaxPriceDto findMinAndMaxPriceItemsByCategoryId(Long categoryId) {

        QItemEntity subItemEntity = new QItemEntity("subItemEntity");

        // 최저가 서브쿼리
        List<Tuple> minPriceResults = queryFactory
            .select(
                itemEntity.brand.name,
                itemEntity.price,
                categoryEntity.name
            )
            .from(itemEntity)
            .join(itemEntity.category, categoryEntity)
            .join(itemEntity.brand, brandEntity)
            .where(
                categoryEntity.id.eq(categoryId),
                itemEntity.price.eq(
                    JPAExpressions
                        .select(subItemEntity.price.min())
                        .from(subItemEntity)
                        .where(subItemEntity.category.id.eq(categoryId))
                )
            )
            .fetch();

        // 최고가 서브쿼리
        List<Tuple> maxPriceResults = queryFactory
            .select(
                itemEntity.brand.name,
                itemEntity.price,
                categoryEntity.name
            )
            .from(itemEntity)
            .join(itemEntity.category, categoryEntity)
            .join(itemEntity.brand, brandEntity)
            .where(
                categoryEntity.id.eq(categoryId),
                itemEntity.price.eq(
                    JPAExpressions
                        .select(subItemEntity.price.max())
                        .from(subItemEntity)
                        .where(subItemEntity.category.id.eq(categoryId))
                )
            )
            .fetch();

        List<BrandPriceDto> lowestPrice = minPriceResults.stream()
            .map(tuple -> new BrandPriceDto(tuple.get(itemEntity.brand.name), tuple.get(itemEntity.price)))
            .toList();

        List<BrandPriceDto> highestPrice = maxPriceResults.stream()
            .map(tuple -> new BrandPriceDto(tuple.get(itemEntity.brand.name), tuple.get(itemEntity.price)))
            .toList();

        String categoryName = minPriceResults.stream().map(tuple -> tuple.get(categoryEntity.name)).findFirst().orElse(null);

        return new CategoryMinMaxPriceDto(
            categoryName,
            lowestPrice,
            highestPrice);
    }
}

package com.example.project.repository.jpa.repository.item;

import com.example.project.repository.jpa.entity.item.ItemEntity;
import com.example.project.repository.jpa.repository.item.model.CategoryPriceDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemJpaRepository extends JpaRepository<ItemEntity, Long>, CustomItemJpaRepository {
    @Query(
        value = """
    WITH MIN_PRICE_GROUP_BY_CATEGORY_ID AS (
        SELECT
            CATEGORY_ID,
            MIN(PRICE) AS MIN_PRICE
        FROM ITEM
        GROUP BY CATEGORY_ID
    )
    SELECT
        i.CATEGORY_ID,
        c.NAME AS CATEGORY_NAME,
        b.NAME AS BRAND_NAME,
        i.PRICE
    FROM CATEGORY c
    INNER JOIN ITEM i
        ON i.CATEGORY_ID = c.ID
    INNER JOIN "BRAND" b
        ON i.BRAND_ID = b.ID
    INNER JOIN MIN_PRICE_GROUP_BY_CATEGORY_ID pc
        ON i.CATEGORY_ID = pc.CATEGORY_ID
        AND i.PRICE = pc.MIN_PRICE
    ORDER BY i.CATEGORY_ID;
""",    nativeQuery = true)
    List<CategoryPriceDto> findMinPriceGroupByCategoryName();
}

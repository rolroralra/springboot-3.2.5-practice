package com.example.project.repository.jpa.repository.item;

import com.example.project.repository.jpa.entity.item.ItemEntity;
import com.example.project.repository.jpa.repository.item.model.CategoryPriceModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ItemJpaRepository extends JpaRepository<ItemEntity, Long>, CustomItemJpaRepository {

    List<ItemEntity> findAllByBrand_Id(Long brandId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE ItemEntity i SET i.brand = null WHERE i.brand.id = :brandId")
    void setNoBrandByBrandId(Long brandId);

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
    List<CategoryPriceModel> findMinPriceGroupByCategoryName();
}

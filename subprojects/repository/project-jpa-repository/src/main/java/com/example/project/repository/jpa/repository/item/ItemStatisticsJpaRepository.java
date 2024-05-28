package com.example.project.repository.jpa.repository.item;

import com.example.project.repository.jpa.entity.item.ItemEntity;
import com.example.project.repository.jpa.repository.item.model.BrandMinPriceSummationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemStatisticsJpaRepository extends JpaRepository<ItemEntity, Long>{
    @Query("""
    SELECT
        a.brandId as brandId,
        a.brandName as brandName,
        SUM(a.minPrice) as minPriceSummation
    FROM (
        SELECT  i.brand.id as brandId, i.brand.name as brandName, i.category.id as categoryaId, MIN(i.price) as minPrice
        FROM    ItemEntity i
        GROUP BY i.brand.id, i.category.id
        ) AS a
    GROUP BY
        a.brandId
    ORDER BY
        SUM(a.minPrice) ASC
    LIMIT
        1
    """)
    BrandMinPriceSummationModel findBrandIdHavingMinPriceSummation();
}

package com.example.project.repository.jpa.repository.brand;

import com.example.project.repository.jpa.entity.brand.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BrandJpaRepository extends JpaRepository<BrandEntity, Long>, CustomBrandJpaRepository {

    @Query(value = """
        SELECT b.name
        FROM BrandEntity b
        WHERE b.id = :brandId
    """)
    String findBrandNameById(@Param("brandId") Long brandId);
}

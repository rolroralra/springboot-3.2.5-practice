package com.example.project.repository.jpa.repository.category;

import com.example.project.repository.jpa.entity.category.CategoryEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long>, CustomCategoryJpaRepository {
    @Query(value = """
        SELECT c.name
        FROM CategoryEntity c
        WHERE c.id = :categoryId
    """)
    String findCategoryNameById(@Param("categoryId") Long categoryId);

    @Query(value = """
        SELECT c.id
        FROM CategoryEntity c
        WHERE c.name = :categoryName
    """)
    Optional<Long> findCategoryIdByName(@Param("categoryName") String categoryName);
}

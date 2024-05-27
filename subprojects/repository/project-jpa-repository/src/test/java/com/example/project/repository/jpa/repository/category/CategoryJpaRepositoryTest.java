package com.example.project.repository.jpa.repository.category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.example.project.repository.jpa.entity.category.CategoryEntity;
import com.example.project.repository.jpa.repository.AbstractJpaBaseTimeEntityRepositoryTest;
import org.junit.jupiter.api.Test;
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

    @Test
    void findCategoryNames() {
        // when
        var categoryNames = categoryJpaRepository.findAllCategoryNames();

        // then
        assertAll(
            () -> assertThat(categoryNames).containsExactlyInAnyOrder(
                "상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "액세서리"
            )
        );
    }

    @Test
    void findCategoryNameById() {
        // given
        Long categoryId = 1L;
        String expectedCategoryName = "상의";

        // when
        String categoryName = categoryJpaRepository.findCategoryNameById(categoryId);

        // then
        assertAll(
            () -> assertThat(categoryName).isEqualTo(expectedCategoryName)
        );
    }
}

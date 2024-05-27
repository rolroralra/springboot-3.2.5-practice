package com.example.project.repository.jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.project.repository.jpa.entity.BaseTimeEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public abstract class AbstractJpaBaseTimeEntityRepositoryTest<T extends BaseTimeEntity, ID> extends AbstractJpaRepositoryTest<T, ID> {
    @Test
    @DisplayName("Auditing 기능이 동작한다.")
    void testJpaAuditing() {
        // Given
        T entity = createTestInstance();

        // When
        T savedEntity = repository().save(entity);
        repository().flush();

        // Then
        assertThat(savedEntity.getInsertAt()).isNotNull();
        assertThat(savedEntity.getUpdateAt()).isNotNull();
    }
}

package com.example.project.repository.jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.project.repository.jpa.config.HibernateJpaConfig;
import com.example.project.repository.jpa.config.TestHibernateJpaConfig;
import jakarta.persistence.Id;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.hibernate.stat.Statistics;
import org.hibernate.tool.schema.spi.SqlScriptException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

@DataJpaTest
@Import({TestHibernateJpaConfig.class, HibernateJpaConfig.class})
public abstract class AbstractJpaRepositoryTest<T, ID> {
    @Autowired
    private Statistics statistics;

    protected abstract JpaRepository<T, ID> repository();

    protected abstract T createTestInstance();

    @SuppressWarnings("unchecked")
    protected ID idFromEntity(T entity) throws IllegalAccessException {
        Field idField = Arrays.stream(entity.getClass().getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(Id.class))
            .findFirst()
            .orElseThrow(() ->
                new SqlScriptException(
                    entity.getClass().getName() + " Entity does not have @Id field")
            );

        idField.setAccessible(true);
        return (ID) idField.get(entity);
    }

    @BeforeEach
    void beforeEach() {
        clearJpaStatistics();
    }

    protected void clearJpaStatistics() {
        statistics.clear();
    }

    protected long getQueryExecutionCount() {
        return statistics.getQueryExecutionCount();
    }

    @SuppressWarnings("unused")
    protected void logJpaSummary() {
        statistics.logSummary();
    }

    @SuppressWarnings("unused")
    protected void assertThatQueryExecutionCountIs(int expected) {
        assertThat(getQueryExecutionCount()).isEqualTo(expected);
    }

    @Test
    protected void findById() throws IllegalAccessException {
        // given
        T entity = createTestInstance();
        T savedEntity = repository().save(entity);

        // when
        Optional<T> findById = repository().findById(idFromEntity(savedEntity));

        // then
        assertThat(findById)
            .isNotEmpty()
            .get()
            .usingRecursiveComparison()
            .isEqualTo(savedEntity);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ':', value= {"0:10", "1:10", "2:10", "3:10"})
    protected void findAll(int page, int size) {
        // given
        List<T> expectedEntityList = repository().findAll(PageRequest.of(0, (page + 1) * size))
            .stream()
            .skip(((long) page) * size)
            .limit(size)
            .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(page, size);

        // when
        Page<T> result = repository().findAll(pageRequest);

        // then
        assertThat(result.getContent())
            .containsExactlyElementsOf(expectedEntityList);
    }

    @Test
    protected void save() {
        // given
        T entity = createTestInstance();

        // when
        T savedEntity = repository().save(entity);

        // then
        assertThat(savedEntity)
            .isNotNull();
    }

    @Test
    protected void delete() throws IllegalAccessException {
        // given
        T entity = createTestInstance();
        T savedEntity = repository().save(entity);

        // when
        repository().deleteById(idFromEntity(savedEntity));
        Optional<T> optionalUser = repository().findById(idFromEntity(savedEntity));

        // then
        assertThat(optionalUser).isNotPresent();
    }
}

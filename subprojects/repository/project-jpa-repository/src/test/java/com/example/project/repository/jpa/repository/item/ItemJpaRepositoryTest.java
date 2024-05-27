package com.example.project.repository.jpa.repository.item;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.project.repository.jpa.entity.item.ItemEntity;
import com.example.project.repository.jpa.repository.AbstractJpaBaseTimeEntityRepositoryTest;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ItemJpaRepositoryTest extends AbstractJpaBaseTimeEntityRepositoryTest<ItemEntity, Long> {
    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Override
    protected ItemJpaRepository repository() {
        return itemJpaRepository;
    }

    @Override
    protected ItemEntity createTestInstance() {
        return new ItemEntity("item1", 1000L);
    }

    @Test
    void findAllByBrand_Id() {
        // given
        Long givenBrandId = 1L;

        // when
        List<ItemEntity> items = itemJpaRepository.findAllByBrand_Id(1L);

        // then
        assertThat(items)
            .isNotEmpty()
            .allMatch(item -> Objects.equals(item.getBrand().getId(), givenBrandId));
    }
}

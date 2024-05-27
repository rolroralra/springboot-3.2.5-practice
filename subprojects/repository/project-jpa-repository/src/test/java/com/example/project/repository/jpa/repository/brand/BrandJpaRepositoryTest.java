package com.example.project.repository.jpa.repository.brand;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.project.repository.jpa.entity.brand.BrandEntity;
import com.example.project.repository.jpa.repository.AbstractJpaBaseTimeEntityRepositoryTest;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BrandJpaRepositoryTest extends AbstractJpaBaseTimeEntityRepositoryTest<BrandEntity, Long> {
    @Autowired
    private BrandJpaRepository brandJpaRepository;

    @Override
    protected BrandJpaRepository repository() {
        return brandJpaRepository;
    }

    @Override
    protected BrandEntity createTestInstance() {
        return new BrandEntity("brand-" + UUID.randomUUID());
    }

    @Test
    void findBrandNameById() {
        // given
        Long givenBrandId = 1L;
        String expectedBrandName = "A";

        // when
        String brandName = brandJpaRepository.findBrandNameById(givenBrandId);

        // then
        assertThat(brandName).isEqualTo(expectedBrandName);
    }

    @Test
    void findAllBrandNames() {
        // when
        var brandNames = brandJpaRepository.findAllBrandNames();

        // then
        assertThat(brandNames).containsExactlyInAnyOrder(
            "A", "B", "C", "D", "E", "F", "G", "H", "I"
        );
    }
}

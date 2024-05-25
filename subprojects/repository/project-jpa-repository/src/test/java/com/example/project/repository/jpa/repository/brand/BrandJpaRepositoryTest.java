package com.example.project.repository.jpa.repository.brand;

import com.example.project.repository.jpa.entity.brand.BrandEntity;
import com.example.project.repository.jpa.repository.AbstractJpaBaseTimeEntityRepositoryTest;
import java.util.UUID;
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
}

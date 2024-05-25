package com.example.project.repository.jpa.repository.item;

import com.example.project.repository.jpa.entity.item.ItemEntity;
import com.example.project.repository.jpa.repository.AbstractJpaBaseTimeEntityRepositoryTest;
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
}

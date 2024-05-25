package com.example.project.repository.jpa.repository.item;

import com.example.project.repository.jpa.entity.item.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories()
public interface ItemJpaRepository extends JpaRepository<ItemEntity, Long> {

}

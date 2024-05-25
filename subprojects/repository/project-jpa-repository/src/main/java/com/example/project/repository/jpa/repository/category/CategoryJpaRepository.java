package com.example.project.repository.jpa.repository.category;

import com.example.project.repository.jpa.entity.category.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories()
public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {

}

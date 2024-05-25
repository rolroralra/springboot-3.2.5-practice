package com.example.project.repository.jpa.repository.brand;

import com.example.project.repository.jpa.entity.brand.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories()
public interface BrandJpaRepository extends JpaRepository<BrandEntity, Long> {

}

package com.example.project.repository.jpa.repository.brand;

import java.util.List;

public interface CustomBrandJpaRepository {
    List<String> findAllBrandNames();
}

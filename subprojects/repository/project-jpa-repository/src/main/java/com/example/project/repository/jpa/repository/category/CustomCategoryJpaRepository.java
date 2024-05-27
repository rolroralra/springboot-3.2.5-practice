package com.example.project.repository.jpa.repository.category;

import java.util.List;

public interface CustomCategoryJpaRepository {
    List<String> findAllCategoryNames();
}

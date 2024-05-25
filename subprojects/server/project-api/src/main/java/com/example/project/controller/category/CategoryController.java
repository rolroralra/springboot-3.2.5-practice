package com.example.project.controller.category;

import com.example.project.controller.category.dto.CategoryResponseDto;
import com.example.project.repository.jpa.repository.category.CategoryJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryJpaRepository categoryJpaRepository;

    @GetMapping
    public List<CategoryResponseDto.Get> getItems(Pageable pageable) {
        return categoryJpaRepository.findAll(pageable).stream()
            .map(CategoryResponseDto.Get::fromEntity)
            .toList();
    }

    @GetMapping("/{categoryId}")
    public CategoryResponseDto.Get getCategoryById(@PathVariable Long categoryId) {
        return CategoryResponseDto.Get.fromEntity(
            categoryJpaRepository.findById(categoryId).orElseThrow()
        );
    }
}

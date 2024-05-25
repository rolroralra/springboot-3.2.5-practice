package com.example.project.controller.category.dto;

import com.example.project.repository.jpa.entity.category.CategoryEntity;
import java.time.LocalDateTime;
import lombok.Builder;

public class CategoryResponseDto {
    @Builder
    public record Get (
        Long id,
        String name,
        LocalDateTime insertAt,
        LocalDateTime updateAt
    ) {

        public static Get fromEntity(CategoryEntity categoryEntity) {
            return Get.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .insertAt(categoryEntity.getInsertAt())
                .updateAt(categoryEntity.getUpdateAt())
                .build();
        }
    }
}

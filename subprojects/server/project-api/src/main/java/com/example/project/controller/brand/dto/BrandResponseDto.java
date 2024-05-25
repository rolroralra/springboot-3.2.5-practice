package com.example.project.controller.brand.dto;

import com.example.project.repository.jpa.entity.brand.BrandEntity;
import java.time.LocalDateTime;
import lombok.Builder;

public class BrandResponseDto {
    @Builder
    public record Get (
        Long id,
        String name,
        LocalDateTime insertAt,
        LocalDateTime updateAt
    ) {

        public static Get fromEntity(BrandEntity brandEntity) {
            return Get.builder()
                .id(brandEntity.getId())
                .name(brandEntity.getName())
                .insertAt(brandEntity.getInsertAt())
                .updateAt(brandEntity.getUpdateAt())
                .build();
        }
    }
}

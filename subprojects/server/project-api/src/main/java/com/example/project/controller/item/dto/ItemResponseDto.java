package com.example.project.controller.item.dto;

import com.example.project.repository.jpa.entity.item.ItemEntity;
import lombok.Builder;

public class ItemResponseDto {
    @Builder
    public record Get (
        Long id,
        String brandName,
        String categoryName,
        String name,
        Long price
    ) {

        public static Get fromEntity(ItemEntity itemEntity) {
            return Get.builder()
                .id(itemEntity.getId())
                .brandName(itemEntity.getBrandName())
                .categoryName(itemEntity.getCategoryName())
                .name(itemEntity.getName())
                .price(itemEntity.getPrice())
                .build();
        }
    }
}

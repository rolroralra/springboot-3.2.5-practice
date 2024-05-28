package com.example.project.controller.item.dto;

import com.example.project.exception.NotFoundException;
import com.example.project.repository.jpa.repository.item.model.BrandPriceModel;
import com.example.project.repository.jpa.repository.item.model.CategoryBrandMinPriceModel;
import com.example.project.repository.jpa.repository.item.model.CategoryMinPriceSummationModel;
import java.util.List;

public record MinBrandPriceGroupByCategoryResponseDto(
    Long totalMinPrice,
    List<CategoryBrandPriceDto> categoryBrandPriceDtos
) {

    public static MinBrandPriceGroupByCategoryResponseDto from(
        CategoryMinPriceSummationModel categoryMinPriceSummationModel) {

        return new MinBrandPriceGroupByCategoryResponseDto(
            categoryMinPriceSummationModel.totalMinPrice(),
            categoryMinPriceSummationModel.categoryBrandMinPriceModels().stream()
                .map(CategoryBrandPriceDto::from)
                .toList()
        );
    }

    public record CategoryBrandPriceDto(
        String categoryName,
        String brandName,
        Long price
    ) {
        public static CategoryBrandPriceDto from(
            CategoryBrandMinPriceModel categoryBrandMinPriceModel) {

            BrandPriceModel brandPriceModel = categoryBrandMinPriceModel.minBrandPrice().stream()
                .findAny()
                .orElseThrow(() -> new NotFoundException(BrandPriceModel.class));

            return new CategoryBrandPriceDto(
                categoryBrandMinPriceModel.categoryName(),
                brandPriceModel.brandName(),
                brandPriceModel.price()
            );
        }
    }
}

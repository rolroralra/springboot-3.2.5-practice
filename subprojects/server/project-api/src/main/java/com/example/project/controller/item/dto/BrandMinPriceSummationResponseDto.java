package com.example.project.controller.item.dto;

import com.example.project.repository.jpa.repository.item.model.BrandMinPriceSummationModel;
import com.example.project.repository.jpa.repository.item.model.CategoryPriceModel;
import java.util.List;

public record BrandMinPriceSummationResponseDto(
    String brandName,
    Long minPriceSummation,
    List<CategoryPriceDto> categoryPriceDtos
) {
    public static BrandMinPriceSummationResponseDto from(
        BrandMinPriceSummationModel brandIdHavingMinPriceSummation,
        List<CategoryPriceModel> categoryPriceModels
    ) {
        return new BrandMinPriceSummationResponseDto(
            brandIdHavingMinPriceSummation.getBrandName(),
            brandIdHavingMinPriceSummation.getMinPriceSummation(),
            categoryPriceModels.stream()
                .map(CategoryPriceDto::from)
                .toList()
        );
    }

    public record CategoryPriceDto(
        String categoryName,
        Long price
    ) {
        public static CategoryPriceDto from(CategoryPriceModel categoryPriceModel) {
            return new CategoryPriceDto(
                categoryPriceModel.categoryName(),
                categoryPriceModel.itemPrice()
            );
        }
    }

}

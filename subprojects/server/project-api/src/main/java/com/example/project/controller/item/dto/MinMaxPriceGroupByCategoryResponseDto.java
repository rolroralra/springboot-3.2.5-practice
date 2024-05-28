package com.example.project.controller.item.dto;

import com.example.project.repository.jpa.repository.item.model.BrandPriceModel;
import com.example.project.repository.jpa.repository.item.model.CategoryMinMaxPriceModel;
import java.util.List;

public record MinMaxPriceGroupByCategoryResponseDto(
    String categoryName,
    List<BrandPriceDto> brandMinPrice,
    List<BrandPriceDto> brandMaxPrice
) {

    public static MinMaxPriceGroupByCategoryResponseDto from(
        CategoryMinMaxPriceModel categoryMinMaxPriceModel
    ) {
        return new MinMaxPriceGroupByCategoryResponseDto(
            categoryMinMaxPriceModel.categoryName(),
            categoryMinMaxPriceModel.brandMinPrice().stream()
                .map(BrandPriceDto::from)
                .toList(),
            categoryMinMaxPriceModel.brandMaxPrice().stream()
                .map(BrandPriceDto::from)
                .toList()
        );
    }

    public record BrandPriceDto(
        String brandName,
        Long price
    ) {
        public static BrandPriceDto from(BrandPriceModel brandPriceModel) {
            return new BrandPriceDto(
                brandPriceModel.brandName(),
                brandPriceModel.price()
            );
        }
    }
}

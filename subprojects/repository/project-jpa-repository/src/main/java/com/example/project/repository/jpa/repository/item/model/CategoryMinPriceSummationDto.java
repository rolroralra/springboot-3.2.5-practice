package com.example.project.repository.jpa.repository.item.model;

import java.util.List;
import java.util.stream.Stream;

public record CategoryMinPriceSummationDto(
    List<CategoryBrandMinPriceDto> categoryBrandMinPriceDtos,
    Long totalMinPrice
) {
    public List<String> getCategoryNames() {
        return categoryBrandMinPriceDtos.stream()
            .map(CategoryBrandMinPriceDto::categoryName)
            .toList();
    }

    public List<String> getBrandNames() {
        return brandPriceDtoStream()
            .map(BrandPriceDto::brandName)
            .toList();
    }

    public List<Long> getMinPrices() {
        return brandPriceDtoStream()
            .map(BrandPriceDto::price)
            .toList();
    }

    private Stream<BrandPriceDto> brandPriceDtoStream() {
        return categoryBrandMinPriceDtos.stream()
            .map(CategoryBrandMinPriceDto::minBrandPrice)
            .flatMap(List::stream);
    }
}

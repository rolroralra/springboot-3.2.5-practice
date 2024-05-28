package com.example.project.repository.jpa.repository.item.model;

import java.util.List;
import java.util.stream.Stream;

public record CategoryMinPriceSummationModel(
    List<CategoryBrandMinPriceModel> categoryBrandMinPriceModels,
    Long totalMinPrice
) {
    public List<String> getCategoryNames() {
        return categoryBrandMinPriceModels.stream()
            .map(CategoryBrandMinPriceModel::categoryName)
            .toList();
    }

    public List<String> getBrandNames() {
        return brandPriceDtoStream()
            .map(BrandPriceModel::brandName)
            .toList();
    }

    public List<Long> getMinPrices() {
        return brandPriceDtoStream()
            .map(BrandPriceModel::price)
            .toList();
    }

    private Stream<BrandPriceModel> brandPriceDtoStream() {
        return categoryBrandMinPriceModels.stream()
            .map(CategoryBrandMinPriceModel::minBrandPrice)
            .flatMap(List::stream);
    }
}

package com.example.project.repository.jpa.repository.item.model;

import java.util.List;

public record CategoryBrandMinPriceModel(
    String categoryName,
    List<BrandPriceModel> minBrandPrice
) {

}

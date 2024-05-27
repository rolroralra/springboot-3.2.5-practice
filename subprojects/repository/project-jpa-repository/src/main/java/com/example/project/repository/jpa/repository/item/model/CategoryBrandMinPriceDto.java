package com.example.project.repository.jpa.repository.item.model;

import java.util.List;

public record CategoryBrandMinPriceDto(
    String categoryName,
    List<BrandPriceDto> minBrandPrice
) {

}

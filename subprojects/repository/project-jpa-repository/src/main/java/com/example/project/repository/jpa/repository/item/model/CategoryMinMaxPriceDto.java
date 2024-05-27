package com.example.project.repository.jpa.repository.item.model;

import java.util.List;

public record CategoryMinMaxPriceDto(
    String categoryName,
    List<BrandPriceDto> brandMinPrice,
    List<BrandPriceDto> brandMaxPrice
) {


}

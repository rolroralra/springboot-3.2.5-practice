package com.example.project.repository.jpa.repository.item.model;

import java.util.List;

public record CategoryMinMaxPriceModel(
    String categoryName,
    List<BrandPriceModel> brandMinPrice,
    List<BrandPriceModel> brandMaxPrice
) {


}

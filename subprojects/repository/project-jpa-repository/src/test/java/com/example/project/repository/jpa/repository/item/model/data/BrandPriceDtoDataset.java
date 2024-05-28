package com.example.project.repository.jpa.repository.item.model.data;

import com.example.project.repository.jpa.repository.item.model.BrandPriceModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BrandPriceDtoDataset {

    public static BrandPriceModel testData(String brandName, Long price) {
        return new BrandPriceModel(brandName, price);
    }
}

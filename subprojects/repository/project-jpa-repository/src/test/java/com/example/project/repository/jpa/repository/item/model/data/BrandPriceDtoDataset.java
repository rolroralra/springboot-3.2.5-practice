package com.example.project.repository.jpa.repository.item.model.data;

import com.example.project.repository.jpa.repository.item.model.BrandPriceDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BrandPriceDtoDataset {

    public static BrandPriceDto testData(String brandName, Long price) {
        return new BrandPriceDto(brandName, price);
    }
}

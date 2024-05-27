package com.example.project.repository.jpa.repository.item.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class CategoryMinPriceSummationDtoTest {
    CategoryMinPriceSummationDto categoryMinPriceSummationDto =
        new CategoryMinPriceSummationDto(
            List.of(
                new CategoryBrandMinPriceDto(
                    "상의",
                    List.of(
                        new BrandPriceDto("A", 1000L),
                        new BrandPriceDto("B", 2000L),
                        new BrandPriceDto("C", 3000L),
                        new BrandPriceDto("A", 2000L)
                    )
                ),
                new CategoryBrandMinPriceDto(
                    "아우터",
                    List.of(
                        new BrandPriceDto("A", 3000L),
                        new BrandPriceDto("B", 2000L),
                        new BrandPriceDto("C", 1000L),
                        new BrandPriceDto("B", 4000L)
                    )
                ),
                new CategoryBrandMinPriceDto(
                    "바지",
                    List.of(
                        new BrandPriceDto("A", 2000L),
                        new BrandPriceDto("B", 2000L),
                        new BrandPriceDto("B", 3000L),
                        new BrandPriceDto("C", 4000L)
                    )
                )
            ), 5000L
        );

    @Test
    void getCategoryNames() {
        // when
        List<String> categoryNames = categoryMinPriceSummationDto.getCategoryNames();

        // then
        assertThat(categoryNames).containsExactly("상의", "아우터", "바지");
    }

    @Test
    void getBrandNames() {
        // when
        List<String> brandNames = categoryMinPriceSummationDto.getBrandNames();

        // then
        assertThat(brandNames).containsExactly(
            "A", "B", "C", "A",
            "A", "B", "C", "B",
            "A", "B", "B", "C"
        );
    }

    @Test
    void getMinPrices() {
        // when
        List<Long> minPrices = categoryMinPriceSummationDto.getMinPrices();

        // then
        assertThat(minPrices).containsExactly(
            1000L, 2000L, 3000L, 2000L,
            3000L, 2000L, 1000L, 4000L,
            2000L, 2000L, 3000L, 4000L
        );
    }
}

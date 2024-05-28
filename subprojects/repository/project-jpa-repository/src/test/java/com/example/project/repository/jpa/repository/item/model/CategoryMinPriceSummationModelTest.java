package com.example.project.repository.jpa.repository.item.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class CategoryMinPriceSummationModelTest {
    CategoryMinPriceSummationModel categoryMinPriceSummationModel =
        new CategoryMinPriceSummationModel(
            List.of(
                new CategoryBrandMinPriceModel(
                    "상의",
                    List.of(
                        new BrandPriceModel("A", 1000L),
                        new BrandPriceModel("B", 2000L),
                        new BrandPriceModel("C", 3000L),
                        new BrandPriceModel("A", 2000L)
                    )
                ),
                new CategoryBrandMinPriceModel(
                    "아우터",
                    List.of(
                        new BrandPriceModel("A", 3000L),
                        new BrandPriceModel("B", 2000L),
                        new BrandPriceModel("C", 1000L),
                        new BrandPriceModel("B", 4000L)
                    )
                ),
                new CategoryBrandMinPriceModel(
                    "바지",
                    List.of(
                        new BrandPriceModel("A", 2000L),
                        new BrandPriceModel("B", 2000L),
                        new BrandPriceModel("B", 3000L),
                        new BrandPriceModel("C", 4000L)
                    )
                )
            ), 5000L
        );

    @Test
    void getCategoryNames() {
        // when
        List<String> categoryNames = categoryMinPriceSummationModel.getCategoryNames();

        // then
        assertThat(categoryNames).containsExactly("상의", "아우터", "바지");
    }

    @Test
    void getBrandNames() {
        // when
        List<String> brandNames = categoryMinPriceSummationModel.getBrandNames();

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
        List<Long> minPrices = categoryMinPriceSummationModel.getMinPrices();

        // then
        assertThat(minPrices).containsExactly(
            1000L, 2000L, 3000L, 2000L,
            3000L, 2000L, 1000L, 4000L,
            2000L, 2000L, 3000L, 4000L
        );
    }
}

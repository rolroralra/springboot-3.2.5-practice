package com.example.project.controller.item.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.example.project.controller.item.dto.BrandMinPriceSummationResponseDto;
import com.example.project.controller.item.dto.BrandMinPriceSummationResponseDto.CategoryPriceDto;
import com.example.project.controller.item.dto.MinBrandPriceGroupByCategoryResponseDto;
import com.example.project.controller.item.dto.MinBrandPriceGroupByCategoryResponseDto.CategoryBrandPriceDto;
import com.example.project.controller.item.dto.MinMaxPriceGroupByCategoryResponseDto;
import com.google.gson.Gson;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemStatisticsSteps {

    public static void 카테고리_별_최저가격_브랜드와_상품_가격_그리고_총액_조회_API_응답_검증(
        MinBrandPriceGroupByCategoryResponseDto responseDto) {
        List<String> categoryNames = responseDto.categoryBrandPriceDtos().stream()
            .map(CategoryBrandPriceDto::categoryName)
            .toList();

        List<String> brandNames = responseDto.categoryBrandPriceDtos().stream()
            .map(CategoryBrandPriceDto::brandName)
            .toList();

        List<Long> itemPrices = responseDto.categoryBrandPriceDtos().stream()
            .map(CategoryBrandPriceDto::price)
            .toList();

        assertAll(
            () -> assertThat(responseDto.totalMinPrice()).isEqualTo(34_100L),
            () -> assertThat(categoryNames).containsExactly("상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "액세서리"),
            () -> assertThat(brandNames).containsExactly(
                "C", "E", "D", "G", "A", "D", "I", "F"
            ),
            () -> assertThat(itemPrices).containsExactly(
                10_000L, 5_000L, 3_000L, 9_000L, 2_000L, 1_500L, 1_700L, 1_900L)
        );
    }

    public static void 단일_브랜드로_모든_카테고리_상품을_구매할_때_최저가격에_판매하는_브랜드와_카테고리의_상품가격_그리고_총액을_조회하는_API_검증(
        BrandMinPriceSummationResponseDto responseDto) {
        List<String> categoryNames = responseDto.categoryPriceDtos().stream()
            .map(CategoryPriceDto::categoryName)
            .toList();

        List<Long> itemPrices = responseDto.categoryPriceDtos().stream()
            .map(CategoryPriceDto::price)
                .toList();

        assertAll(
            () -> assertThat(responseDto)
                .isNotNull()
                .hasFieldOrPropertyWithValue("brandName", "D")
                .hasFieldOrPropertyWithValue("minPriceSummation", 36100L),
            () -> assertThat(categoryNames).containsExactly(
                "상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "액세서리"
            ),
            () -> assertThat(itemPrices).containsExactly(
                10_100L, 5_100L, 3_000L, 9_500L, 2_500L, 1_500L, 2_400L, 2_000L
            )
        );
    }

    public static void 카테고리_이름으로_최저_최고_가격_브랜드와_상품_가격을_조회하는_API_응답_검증(
        MinMaxPriceGroupByCategoryResponseDto responseDto) {
        MinMaxPriceGroupByCategoryResponseDto expectedResponseDto = fromJson("""
            {
                "categoryName": "상의",
                "brandMinPrice": [
                    {
                        "brandName": "C",
                        "price": 10000
                    }
                ],
                "brandMaxPrice": [
                    {
                        "brandName": "I",
                        "price": 11400
                    }
                ]
            }
            """, MinMaxPriceGroupByCategoryResponseDto.class);

        assertThat(responseDto).isEqualTo(expectedResponseDto);
    }

    private static <T> T fromJson(String json, Class<T> clazz) {
        System.out.println(json);
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

}

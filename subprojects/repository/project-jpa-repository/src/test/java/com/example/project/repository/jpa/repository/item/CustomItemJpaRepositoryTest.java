package com.example.project.repository.jpa.repository.item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.example.project.repository.jpa.config.HibernateJpaConfig;
import com.example.project.repository.jpa.config.TestHibernateJpaConfig;
import com.example.project.repository.jpa.repository.category.CategoryJpaRepository;
import com.example.project.repository.jpa.repository.item.model.BrandMinPriceSummationModel;
import com.example.project.repository.jpa.repository.item.model.CategoryMinMaxPriceModel;
import com.example.project.repository.jpa.repository.item.model.CategoryMinPriceSummationModel;
import com.example.project.repository.jpa.repository.item.model.CategoryPriceModel;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({TestHibernateJpaConfig.class, HibernateJpaConfig.class})
class CustomItemJpaRepositoryTest {
    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Autowired
    private ItemStatisticsJpaRepository itemStatisticsJpaRepository;

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;


    @Test
    void 구현1_카테고리_별_최저가격_브랜드와_상품_가격과_총액을_조회할_수_있다() {
        // when
        CategoryMinPriceSummationModel result = itemJpaRepository.findMinPriceGroupByCategoryId();

        // then
        List<String> categoryNames = result.getCategoryNames();

        List<String> brandNames = result.getBrandNames();

        List<Long> itemPrices = result.getMinPrices();

        long totalMinPrice = itemPrices.stream().mapToLong(Long::longValue).sum();

        assertAll(
            () -> assertThat(totalMinPrice).isEqualTo(34_100L),
            () -> assertThat(categoryNames).containsExactly(
                "상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "액세서리"
            ),
            () -> assertThat(brandNames).containsExactly(
                "C", "E", "D", "G", "A", "D", "I", "F"
            ),
            () -> assertThat(itemPrices).containsExactly(
                10_000L, 5_000L, 3_000L, 9_000L, 2_000L, 1_500L, 1_700L, 1_900L)
        );
    }

    @Test
    void 구현2_단일_브랜드로_모든_카테고리_상품을_구매할_때_최저가격에_판매하는_브랜드와_카테고리의_상품가격과_총액을_조회할_수_있다() {
        // when
        BrandMinPriceSummationModel brandIdHavingMinPriceSummation
            = itemStatisticsJpaRepository.findBrandIdHavingMinPriceSummation();

        List<CategoryPriceModel> categoryPriceModels =
            itemJpaRepository.findMinPriceGroupByCategoryNameWhereBrandIdEq(
                brandIdHavingMinPriceSummation.getBrandId()
            );

        // then
        long itemPriceSum = categoryPriceModels.stream()
            .mapToLong(CategoryPriceModel::itemPrice)
            .sum();

        List<String> categoryNames = categoryPriceModels.stream()
            .map(CategoryPriceModel::categoryName)
            .distinct()
            .toList();

        List<Long> itemPriceList = categoryPriceModels.stream()
            .map(CategoryPriceModel::itemPrice)
            .toList();

        assertAll(
            () -> assertThat(brandIdHavingMinPriceSummation)
                .isNotNull()
                .hasFieldOrPropertyWithValue("brandId", 4L)
                .hasFieldOrPropertyWithValue("brandName", "D")
                .hasFieldOrPropertyWithValue("minPriceSummation", 36100L),
            () -> assertThat(itemPriceSum)
                .isEqualTo(brandIdHavingMinPriceSummation.getMinPriceSummation())
                .isEqualTo(36100L),
            () -> assertThat(categoryNames).containsExactly(
                "상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "액세서리"
            ),
            () -> assertThat(itemPriceList).containsExactly(
                10_100L, 5_100L, 3_000L, 9_500L, 2_500L, 1_500L, 2_400L, 2_000L
            )
        );
    }

    @Test
    void 구현3_카테고리_아이디로_최저_최고_가격_브랜드와_상품_가격을_조회() {
        // given
        Long categoryId = 1L;
        String expectedCategoryName = categoryJpaRepository.findCategoryNameById(categoryId);

        // when
        CategoryMinMaxPriceModel lowestAndHighestPriceItemsByCategoryId =
            itemJpaRepository.findMinAndMaxPriceItemsByCategoryId(categoryId);

        // then
        assertAll(
            () -> assertThat(lowestAndHighestPriceItemsByCategoryId).isNotNull(),
            () -> assertThat(lowestAndHighestPriceItemsByCategoryId.categoryName()).isEqualTo(expectedCategoryName),
            () -> lowestAndHighestPriceItemsByCategoryId.brandMinPrice().stream()
                .findFirst()
                .ifPresentOrElse(brandMinPriceDto ->
                        assertAll(
                            () -> assertThat(brandMinPriceDto.price()).isEqualTo(10_000L),
                            () -> assertThat(brandMinPriceDto.brandName()).isEqualTo("C")
                        ),
                    () -> Assertions.fail("최저가격 상품이 존재하지 않습니다.")
                ),
            () -> lowestAndHighestPriceItemsByCategoryId.brandMaxPrice().stream()
                .findFirst()
                .ifPresentOrElse(brandMaxPriceDto ->
                    assertAll(
                        () -> assertThat(brandMaxPriceDto.price()).isEqualTo(11_400L),
                        () -> assertThat(brandMaxPriceDto.brandName()).isEqualTo("I")
                    ),
                    () -> Assertions.fail("최고가격 상품이 존재하지 않습니다.")
            )
        );
    }
}

package com.example.project.controller.item;

import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import com.example.project.controller.AbstractControllerTest;
import com.example.project.controller.item.dto.BrandMinPriceSummationResponseDto;
import com.example.project.controller.item.dto.MinBrandPriceGroupByCategoryResponseDto;
import com.example.project.controller.item.dto.MinMaxPriceGroupByCategoryResponseDto;
import com.example.project.controller.item.steps.ItemStatisticsSteps;
import com.example.project.exception.NotFoundException;
import com.example.project.repository.jpa.entity.item.ItemEntity;
import com.example.project.repository.jpa.repository.item.ItemJpaRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.restdocs.snippet.Snippet;

class ItemStatisticsControllerTest extends AbstractControllerTest {

    @Autowired
    private ItemJpaRepository itemRepository;

    @Override
    protected String identifier() {
        return this.getClass().getSimpleName();
    }

    @Test
    void getMinBrandPriceGroupByCategory() {
        // when
        ExtractableResponse<Response> response =
            given(
                minBrandPriceGroupByCategoryResponseSnippet()
            ).when()
                .get("/api/v1/items/statistics/min-brand-price-group-by-category")
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract();

        // then
        MinBrandPriceGroupByCategoryResponseDto responseDto = response.jsonPath()
            .getObject(".", MinBrandPriceGroupByCategoryResponseDto.class);

        System.out.println(responseDto);
        ItemStatisticsSteps.카테고리_별_최저가격_브랜드와_상품_가격_그리고_총액_조회_API_응답_검증(responseDto);

    }

    @Test
    void getBrandMinPriceSummation() {
        // when
        ExtractableResponse<Response> response =
            given(
                brandMinPriceSummationResponseSnippet()
            ).when()
                .get("/api/v1/items/statistics/min-single-brand-price")
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract();

        // then
        BrandMinPriceSummationResponseDto responseDto = response.jsonPath()
            .getObject(".", BrandMinPriceSummationResponseDto.class);

        ItemStatisticsSteps.단일_브랜드로_모든_카테고리_상품을_구매할_때_최저가격에_판매하는_브랜드와_카테고리의_상품가격_그리고_총액을_조회하는_API_검증(responseDto);
    }

    @Test
    void getMinMaxPriceGroupByCategory() {
        // given
        String givenCategoryName = "상의";

        // when
        ExtractableResponse<Response> response =
            given(
                queryParameters(
                    parameterWithName("categoryName").description("categoryName")
                ),
                minMaxPriceGroupByCategoryResponseSnippet()
            ).when()
                .param("categoryName", givenCategoryName)
                .get("/api/v1/items/statistics/min-max-price")
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract();

        // then
        MinMaxPriceGroupByCategoryResponseDto responseDto = response.jsonPath()
            .getObject(".", MinMaxPriceGroupByCategoryResponseDto.class);

        ItemStatisticsSteps.카테고리_이름으로_최저_최고_가격_브랜드와_상품_가격을_조회하는_API_응답_검증(responseDto);
        System.out.println(responseDto);
    }

    private Snippet minBrandPriceGroupByCategoryResponseSnippet() {
        return responseFields(
            fieldWithPath("totalMinPrice")
                .type(NUMBER)
                .description("카테고리별 최저가격 총합"),
            fieldWithPath(".categoryBrandPriceDtos[]")
                .type(ARRAY)
                .description("카테고리별 최저가격 브랜드와 상품 가격"),
            fieldWithPath(".categoryBrandPriceDtos[].categoryName")
                .type(STRING)
                .description("카테고리 이름"),
            fieldWithPath(".categoryBrandPriceDtos[].brandName")
                .type(STRING)
                .description("카테고리별 최저가격 브랜드 이름"),
            fieldWithPath(".categoryBrandPriceDtos[].price")
                .type(NUMBER)
                .description("카테고리별 최저가격")
        );
    }

    private Snippet brandMinPriceSummationResponseSnippet() {
        return relaxedResponseFields(
            fieldWithPath("brandName")
                .type(STRING)
                .description("최저가격 총액을 가진 브랜드 이름"),
            fieldWithPath("minPriceSummation")
                .type(NUMBER)
                .description("단일 브랜드 최저가격 총액"),
            fieldWithPath(".categoryPriceDtos[]")
                .type(ARRAY)
                .description("최저가격 총액을 가진 브랜드의 카테고리별 상품 최저가격"),
            fieldWithPath(".categoryPriceDtos[].categoryName")
                .type(STRING)
                .description("카테고리 이름"),
            fieldWithPath(".categoryPriceDtos[].price")
                .type(NUMBER)
                .description("카테고리별 상품 최저가격")
        );
    }

    private Snippet minMaxPriceGroupByCategoryResponseSnippet() {
        return relaxedResponseFields(
            fieldWithPath("categoryName")
                .type(STRING)
                .description("카테고리 이름"),
            fieldWithPath(".brandMinPrice[]")
                .type(ARRAY)
                .description("특정 카테고리의 최저 가격 브랜드와 상품가격"),
            fieldWithPath(".brandMaxPrice[]")
                .type(ARRAY)
                .description("특정 카테고리의 최고 가격 브랜드와 상품가격"),
            fieldWithPath(".brandMinPrice[].brandName")
                .type(STRING)
                .description("특정 카테고리의 최저가격 브랜드 이름"),
            fieldWithPath(".brandMinPrice[].price")
                .type(NUMBER)
                .description("특정 카테고리의 최저가격 브랜드 상품가격"),
            fieldWithPath(".brandMaxPrice[].brandName")
                .type(STRING)
                .description("특정 카테고리의 최고가격 브랜드 이름"),
            fieldWithPath(".brandMaxPrice[].price")
                .type(NUMBER)
                .description("특정 카테고리의 최고가격 브랜드 상품가격")
        );
    }

    private Long getGivenItemId() {
        return itemRepository.findAll(PageRequest.ofSize(10))
            .stream()
            .map(ItemEntity::getId)
            .findFirst()
            .orElseThrow(() -> new NotFoundException(ItemEntity.class));
    }
}

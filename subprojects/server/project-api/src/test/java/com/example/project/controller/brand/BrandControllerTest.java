package com.example.project.controller.brand;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import com.example.project.controller.AbstractControllerTest;
import com.example.project.controller.brand.dto.BrandResponseDto;
import com.example.project.controller.brand.steps.BrandSteps;
import com.example.project.exception.NotFoundException;
import com.example.project.repository.jpa.entity.brand.BrandEntity;
import com.example.project.repository.jpa.repository.brand.BrandJpaRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.restdocs.snippet.Snippet;

class BrandControllerTest extends AbstractControllerTest {

    @Autowired
    private BrandJpaRepository brandJpaRepository;

    @Override
    protected String identifier() {
        return this.getClass().getSimpleName();
    }

    @Test
    void getBrands() {
        // given
        PageRequest givenPageRequest = PageRequest.ofSize(10);
        int givenPageNumber = givenPageRequest.getPageNumber();
        int givenPageSize = givenPageRequest.getPageSize();

        // when
        ExtractableResponse<Response> response =
            given(
                pageableQueryParameterSnippet(),
                brandListResponseFieldsSnippet()
            ).when()
                .queryParam("page", givenPageNumber)
                .queryParam("size", givenPageSize)
                .get("/api/v1/brands")
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract();

        // then
        List<BrandResponseDto.Get> responseDtos
            = response.jsonPath().getList(".", BrandResponseDto.Get.class);
        responseDtos.forEach(BrandSteps::브랜드_응답_검증);
    }

    @Test
    void getCategoryById() {
        // given
        Long givenBrandId = getGivenBrandId();

        // when
        ExtractableResponse<Response> response =
            given(
                pathParameters(
                    parameterWithName("brandId").description("brandId")
                ),
                brandResponseFieldsSnippet()
            ).when()
                .get("/api/v1/brands/{brandId}", givenBrandId)
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract();

        // then
        BrandResponseDto.Get responseDto
            = response.jsonPath().getObject(".", BrandResponseDto.Get.class);
        BrandSteps.브랜드_응답_검증(responseDto);
    }

    @Test
    void createItem() {
        // given
        String givenRequestBody = """
        {
            "name": "nike"
        }
        """;

        // when
        ExtractableResponse<Response> response =
            given(
                requestFields(
                    fieldWithPath("name").description("brandName")
                ),
                brandResponseFieldsSnippet()
            ).when()
                .body(givenRequestBody)
                .post("/api/v1/brands")
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_CREATED)
                .extract();

        // then
        BrandResponseDto.Get responseDto
            = response.jsonPath().getObject(".", BrandResponseDto.Get.class);
        BrandSteps.브랜드_응답_검증(responseDto);

        assertThat(responseDto)
            .hasFieldOrPropertyWithValue("name", "nike");
    }

    @Test
    void updateItemByPutMethod() {
        // given
        Long givenBrandId = getGivenBrandId();
        String givenRequestBody = """
        {
            "name": "nike"
        }
        """;

        // when
        ExtractableResponse<Response> response =
            given(
                pathParameters(
                    parameterWithName("brandId").description("brandId")
                ),
                requestFields(
                    fieldWithPath("name").description("brandName")
                ),
                brandResponseFieldsSnippet()
            ).when()
                .body(givenRequestBody)
                .put("/api/v1/brands/{brandId}", givenBrandId)
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract();

        // then
        BrandResponseDto.Get responseDto
            = response.jsonPath().getObject(".", BrandResponseDto.Get.class);
        BrandSteps.브랜드_응답_검증(responseDto);

        assertThat(responseDto)
            .hasFieldOrPropertyWithValue("id", givenBrandId)
            .hasFieldOrPropertyWithValue("name", "nike");
    }

    @Test
    void updateItemByPatchMethod() {
        // given
        Long givenBrandId = getGivenBrandId();
        String givenRequestBody = """
        {
            "name": "nike"
        }
        """;

        // when
        ExtractableResponse<Response> response =
            given(
                pathParameters(
                    parameterWithName("brandId").description("brandId")
                ),
                requestFields(
                    fieldWithPath("name").description("brandName")
                ),
                brandResponseFieldsSnippet()
            ).when()
                .body(givenRequestBody)
                .patch("/api/v1/brands/{brandId}", givenBrandId)
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract();

        // then
        BrandResponseDto.Get responseDto
            = response.jsonPath().getObject(".", BrandResponseDto.Get.class);
        BrandSteps.브랜드_응답_검증(responseDto);

        assertThat(responseDto)
            .hasFieldOrPropertyWithValue("id", givenBrandId)
            .hasFieldOrPropertyWithValue("name", "nike");
    }

    @Test
    void deleteItem() {
        // given
        Long givenBrandId = getGivenBrandId();

        // expected
        given(
            pathParameters(
                parameterWithName("brandId").description("brandId")
            )
        ).when()
            .delete("/api/v1/brands/{brandId}", givenBrandId)
            .then()
            .log().all()
            .assertThat().statusCode(HttpStatus.SC_OK);
    }

    private Snippet brandResponseFieldsSnippet() {
        return responseFields(
            fieldWithPath("id")
                .type(NUMBER)
                .description("brandId"),
            fieldWithPath("name")
                .type(STRING)
                .description("brandName")
        );
    }

    private Snippet brandListResponseFieldsSnippet() {
        return responseFields(
            fieldWithPath("[]")
                .type(ARRAY)
                .description("brand array"),
            fieldWithPath("[].id")
                .type(NUMBER)
                .description("name"),
            fieldWithPath("[].name")
                .type(STRING)
                .description("brandName")
        );
    }

    private Long getGivenBrandId() {
        return brandJpaRepository.findAll(PageRequest.ofSize(10))
            .stream()
            .map(BrandEntity::getId)
            .findFirst()
            .orElseThrow(() -> new NotFoundException(BrandEntity.class));
    }
}

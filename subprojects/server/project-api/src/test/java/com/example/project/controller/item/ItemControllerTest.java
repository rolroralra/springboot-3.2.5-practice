package com.example.project.controller.item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import com.example.project.controller.AbstractControllerTest;
import com.example.project.controller.item.dto.ItemResponseDto;
import com.example.project.controller.item.steps.ItemSteps;
import com.example.project.exception.NotFoundException;
import com.example.project.repository.jpa.entity.item.ItemEntity;
import com.example.project.repository.jpa.repository.item.ItemJpaRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.restdocs.snippet.Snippet;

class ItemControllerTest extends AbstractControllerTest {

    @Autowired
    private ItemJpaRepository itemRepository;

    @Override
    protected String identifier() {
        return this.getClass().getSimpleName();
    }

    @Test
    void getItems() {
        // given
        PageRequest givenPageRequest = PageRequest.ofSize(10);
        int givenPageNumber = givenPageRequest.getPageNumber();
        int givenPageSize = givenPageRequest.getPageSize();

        // when
        ExtractableResponse<Response> response =
            given(
                pageableQueryParameterSnippet(),
                itemListResponseFieldsSnippet()
            ).when()
                .queryParam("page", givenPageNumber)
                .queryParam("size", givenPageSize)
                .get("/api/v1/items")
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract();

        // then
        List<ItemResponseDto.Get> responseDtos
            = response.jsonPath().getList("content", ItemResponseDto.Get.class);
        responseDtos.forEach(ItemSteps::아이템_응답_검증);
    }

    @Test
    void getCategoryById() {
        // given
        Long givenItemId = getGivenItemId();

        // when
        ExtractableResponse<Response> response =
            given(
                pathParameters(
                    parameterWithName("itemId").description("itemId")
                ),
                itemResponseFieldsSnippet()
            ).when()
                .get("/api/v1/items/{itemId}", givenItemId)
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract();

        // then
        ItemResponseDto.Get responseDto
            = response.jsonPath().getObject(".", ItemResponseDto.Get.class);
        ItemSteps.아이템_응답_검증(responseDto);
    }

    @Test
    void createItem() {
        // given
        String givenRequestBody = """
        {
            "name": "item",
            "price": 1000,
            "categoryId": 1,
            "brandId": 1
        }
        """;

        // when
        ExtractableResponse<Response> response =
            given(
                requestFields(
                    fieldWithPath("name").description("itemName"),
                    fieldWithPath("price").description("itemPrice"),
                    fieldWithPath("categoryId").description("categoryId"),
                    fieldWithPath("brandId").description("brandId")
                ),
                itemResponseFieldsSnippet()
            ).when()
                .body(givenRequestBody)
                .post("/api/v1/items")
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_CREATED)
                .extract();

        // then
        ItemResponseDto.Get responseDto
            = response.jsonPath().getObject(".", ItemResponseDto.Get.class);
        ItemSteps.아이템_응답_검증(responseDto);

        assertThat(responseDto)
            .hasFieldOrPropertyWithValue("name", "item")
            .hasFieldOrPropertyWithValue("price", 1000L)
            .hasFieldOrPropertyWithValue("categoryName", "상의")
            .hasFieldOrPropertyWithValue("brandName", "A");
    }

    @Test
    void updateItemByPutMethod() {
        // given
        Long givenItemId = getGivenItemId();
        String givenRequestBody = """
        {
            "name": "item",
            "price": 1000,
            "categoryId": 1,
            "brandId": 1
        }
        """;

        // when
        ExtractableResponse<Response> response =
            given(
                pathParameters(
                    parameterWithName("itemId").description("itemId")
                ),
                requestFields(
                    fieldWithPath("name").description("itemName"),
                    fieldWithPath("price").description("itemPrice"),
                    fieldWithPath("categoryId").description("categoryId"),
                    fieldWithPath("brandId").description("brandId")
                ),
                itemResponseFieldsSnippet()
            ).when()
                .body(givenRequestBody)
                .put("/api/v1/items/{itemId}", givenItemId)
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract();

        // then
        ItemResponseDto.Get responseDto
            = response.jsonPath().getObject(".", ItemResponseDto.Get.class);
        ItemSteps.아이템_응답_검증(responseDto);

        assertThat(responseDto)
            .hasFieldOrPropertyWithValue("id", givenItemId)
            .hasFieldOrPropertyWithValue("name", "item")
            .hasFieldOrPropertyWithValue("price", 1000L)
            .hasFieldOrPropertyWithValue("categoryName", "상의")
            .hasFieldOrPropertyWithValue("brandName", "A");
    }

    @Test
    void updateItemByPatchMethod() {
        // given
        Long givenItemId = getGivenItemId();
        String givenRequestBody = """
        {
            "name": "item",
            "price": 1000,
            "categoryId": 1,
            "brandId": 1
        }
        """;

        // when
        ExtractableResponse<Response> response =
            given(
                pathParameters(
                    parameterWithName("itemId").description("itemId")
                ),
                requestFields(
                    fieldWithPath("name").description("itemName"),
                    fieldWithPath("price").description("itemPrice"),
                    fieldWithPath("categoryId").description("categoryId"),
                    fieldWithPath("brandId").description("brandId")
                ),
                itemResponseFieldsSnippet()
            ).when()
                .body(givenRequestBody)
                .patch("/api/v1/items/{itemId}", givenItemId)
                .then()
                .log().all()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract();

        // then
        ItemResponseDto.Get responseDto
            = response.jsonPath().getObject(".", ItemResponseDto.Get.class);
        ItemSteps.아이템_응답_검증(responseDto);

        assertThat(responseDto)
            .hasFieldOrPropertyWithValue("id", givenItemId)
            .hasFieldOrPropertyWithValue("name", "item")
            .hasFieldOrPropertyWithValue("price", 1000L)
            .hasFieldOrPropertyWithValue("categoryName", "상의")
            .hasFieldOrPropertyWithValue("brandName", "A");
    }

    @Test
    void deleteItem() {
        // given
        Long givenItemId = getGivenItemId();

        // expected
        given(
            pathParameters(
                parameterWithName("itemId").description("itemId")
            )
        ).when()
            .delete("/api/v1/items/{itemId}", givenItemId)
            .then()
            .log().all()
            .assertThat().statusCode(HttpStatus.SC_OK);
    }

    private Snippet itemResponseFieldsSnippet() {
        return responseFields(
            fieldWithPath("id")
                .type(NUMBER)
                .description("itemId"),
            fieldWithPath("brandName")
                .type(STRING)
                .description("brandName"),
            fieldWithPath("categoryName")
                .type(STRING)
                .description("categoryName"),
            fieldWithPath("name")
                .type(STRING)
                .description("itemName"),
            fieldWithPath("price")
                .type(NUMBER)
                .description("itemPrice")
        );
    }

    private Snippet itemListResponseFieldsSnippet() {
        return relaxedResponseFields(
            fieldWithPath(".content[]")
                .type(ARRAY)
                .description("item array"),
            fieldWithPath(".content[].id")
                .type(NUMBER)
                .description("itemId"),
            fieldWithPath(".content[].brandName")
                .type(STRING)
                .description("brandName"),
            fieldWithPath(".content[].categoryName")
                .type(STRING)
                .description("categoryName"),
            fieldWithPath(".content[].name")
                .type(STRING)
                .description("itemName"),
            fieldWithPath(".content[].price")
                .type(NUMBER)
                .description("itemPrice")
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

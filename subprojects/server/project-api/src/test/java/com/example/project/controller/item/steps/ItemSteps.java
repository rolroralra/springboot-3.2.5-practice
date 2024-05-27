package com.example.project.controller.item.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.example.project.controller.item.dto.ItemResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("all")
public class ItemSteps {

    public static void 아이템_응답_검증(ItemResponseDto.Get get) {
        assertAll(
            () -> assertThat(get).isNotNull(),
            () -> assertThat(get).hasNoNullFieldsOrProperties(),
            () -> assertThat(get.name()).isNotBlank(),
            () -> assertThat(get.brandName()).isNotBlank(),
            () -> assertThat(get.categoryName()).isNotBlank(),
            () -> assertThat(get.price()).isGreaterThanOrEqualTo(0L)
        );
    }
}

package com.example.project.controller.brand.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.example.project.controller.brand.dto.BrandResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("all")
public class BrandSteps {

    public static void 브랜드_응답_검증(BrandResponseDto.Get get) {
        assertAll(
            () -> assertThat(get).isNotNull(),
            () -> assertThat(get).hasNoNullFieldsOrProperties(),
            () -> assertThat(get.name()).isNotBlank()
        );
    }
}

package com.example.project.controller.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ItemRequestDto {
    public record Post(
        @NotBlank String name,
        @NotNull Long brandId,
        @NotNull Long categoryId,
        @Positive Long price
    ) {

    }

    public record Patch(
        @NotBlank String name,
        @NotNull Long brandId,
        @NotNull Long categoryId,
        @Positive Long price
    ) {

    }

    public record Put(
        @NotBlank String name,
        @NotNull Long brandId,
        @NotNull Long categoryId,
        @Positive Long price
    ) {

    }
}

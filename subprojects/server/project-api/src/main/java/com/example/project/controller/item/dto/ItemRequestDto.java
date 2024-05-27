package com.example.project.controller.item.dto;

public class ItemRequestDto {
    public record Post(
        String name,
        Long brandId,
        Long categoryId,
        Long price
    ) {

    }

    public record Patch(
        String name,
        Long brandId,
        Long categoryId,
        Long price
    ) {

    }

    public record Put(
        String name,
        Long brandId,
        Long categoryId,
        Long price
    ) {

    }
}

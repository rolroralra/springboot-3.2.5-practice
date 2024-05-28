package com.example.project.controller.item;

import com.example.project.controller.item.dto.BrandMinPriceSummationResponseDto;
import com.example.project.controller.item.dto.MinBrandPriceGroupByCategoryResponseDto;
import com.example.project.controller.item.dto.MinMaxPriceGroupByCategoryResponseDto;
import com.example.project.service.item.ItemStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items/statistics")
public class ItemStatisticsController {

    private final ItemStatisticsService itemStatisticsService;

    @GetMapping("/min-brand-price-group-by-category")
    public MinBrandPriceGroupByCategoryResponseDto getMinBrandPriceGroupByCategory() {
        return itemStatisticsService.getMinBrandPriceGroupByCategory();
    }

    @GetMapping("/min-single-brand-price")
    public BrandMinPriceSummationResponseDto getBrandMinPriceSummation() {
        return itemStatisticsService.getBrandMinPriceSummation();
    }

    @GetMapping("/min-max-price")
    public MinMaxPriceGroupByCategoryResponseDto getMinMaxPriceGroupByCategoryName(
        @RequestParam("categoryName") String categoryName
    ) {
        return itemStatisticsService.getMinMaxPriceGroupByCategoryName(categoryName);
    }
}

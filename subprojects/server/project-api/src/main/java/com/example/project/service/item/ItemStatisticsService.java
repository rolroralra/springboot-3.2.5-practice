package com.example.project.service.item;

import com.example.project.controller.item.dto.BrandMinPriceSummationResponseDto;
import com.example.project.controller.item.dto.MinBrandPriceGroupByCategoryResponseDto;
import com.example.project.controller.item.dto.MinMaxPriceGroupByCategoryResponseDto;
import com.example.project.exception.NotFoundException;
import com.example.project.repository.jpa.entity.category.CategoryEntity;
import com.example.project.repository.jpa.repository.category.CategoryJpaRepository;
import com.example.project.repository.jpa.repository.item.ItemJpaRepository;
import com.example.project.repository.jpa.repository.item.ItemStatisticsJpaRepository;
import com.example.project.repository.jpa.repository.item.model.BrandMinPriceSummationModel;
import com.example.project.repository.jpa.repository.item.model.CategoryMinMaxPriceModel;
import com.example.project.repository.jpa.repository.item.model.CategoryMinPriceSummationModel;
import com.example.project.repository.jpa.repository.item.model.CategoryPriceModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemStatisticsService {
    private final ItemJpaRepository itemJpaRepository;

    private final ItemStatisticsJpaRepository itemStatisticsJpaRepository;

    private final CategoryJpaRepository categoryJpaRepository;

    @Transactional(readOnly = true)
    public MinBrandPriceGroupByCategoryResponseDto getMinBrandPriceGroupByCategory() {
        CategoryMinPriceSummationModel categoryMinPriceSummationModel
            = itemJpaRepository.findMinPriceGroupByCategoryId();

        return MinBrandPriceGroupByCategoryResponseDto.from(
            categoryMinPriceSummationModel
        );
    }

    @Transactional(readOnly = true)
    public BrandMinPriceSummationResponseDto getBrandMinPriceSummation() {
        BrandMinPriceSummationModel brandIdHavingMinPriceSummation
            = itemStatisticsJpaRepository.findBrandIdHavingMinPriceSummation();

        List<CategoryPriceModel> categoryPriceModels =
            itemJpaRepository.findMinPriceGroupByCategoryNameWhereBrandIdEq(
                brandIdHavingMinPriceSummation.getBrandId()
            );

        return BrandMinPriceSummationResponseDto.from(
            brandIdHavingMinPriceSummation,
            categoryPriceModels
        );
    }

    @Transactional(readOnly = true)
    public MinMaxPriceGroupByCategoryResponseDto getMinMaxPriceGroupByCategoryName(String categoryName) {
        Long categoryId = categoryJpaRepository.findCategoryIdByName(categoryName)
            .orElseThrow(() -> new NotFoundException(CategoryEntity.class));

        CategoryMinMaxPriceModel minAndMaxPrice =
            itemJpaRepository.findMinAndMaxPriceItemsByCategoryId(categoryId);

        return MinMaxPriceGroupByCategoryResponseDto.from(
            minAndMaxPrice
        );
    }
}

package com.example.project.repository.jpa.repository.item;

import com.example.project.repository.jpa.repository.item.model.CategoryMinMaxPriceDto;
import com.example.project.repository.jpa.repository.item.model.CategoryMinPriceSummationDto;
import com.example.project.repository.jpa.repository.item.model.CategoryPriceDto;
import java.util.List;

public interface CustomItemJpaRepository {

    CategoryMinPriceSummationDto findMinPriceGroupByCategoryId();

    List<CategoryPriceDto> findMinPriceGroupByCategoryNameWhereBrandIdEq(Long brandId);

    CategoryMinMaxPriceDto findMinAndMaxPriceItemsByCategoryId(Long categoryId);
}

package com.example.project.repository.jpa.repository.item;

import com.example.project.repository.jpa.repository.item.model.CategoryMinMaxPriceModel;
import com.example.project.repository.jpa.repository.item.model.CategoryMinPriceSummationModel;
import com.example.project.repository.jpa.repository.item.model.CategoryPriceModel;
import java.util.List;

public interface CustomItemJpaRepository {

    CategoryMinPriceSummationModel findMinPriceGroupByCategoryId();

    List<CategoryPriceModel> findMinPriceGroupByCategoryNameWhereBrandIdEq(Long brandId);

    CategoryMinMaxPriceModel findMinAndMaxPriceItemsByCategoryId(Long categoryId);
}

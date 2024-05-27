package com.example.project.service.item;

import com.example.project.controller.item.dto.ItemRequestDto;
import com.example.project.controller.item.dto.ItemResponseDto;
import com.example.project.controller.item.dto.ItemResponseDto.Get;
import com.example.project.exception.NotFoundException;
import com.example.project.repository.jpa.entity.brand.BrandEntity;
import com.example.project.repository.jpa.entity.category.CategoryEntity;
import com.example.project.repository.jpa.entity.item.ItemEntity;
import com.example.project.repository.jpa.repository.brand.BrandJpaRepository;
import com.example.project.repository.jpa.repository.category.CategoryJpaRepository;
import com.example.project.repository.jpa.repository.item.ItemJpaRepository;
import com.google.common.base.Preconditions;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemJpaRepository itemJpaRepository;

    private final BrandJpaRepository brandJpaRepository;

    private final CategoryJpaRepository categoryJpaRepository;

    @Transactional(readOnly = true)
    public ItemResponseDto.Get getItemById(Long itemId) {
        return ItemResponseDto.Get.fromEntity(
            findItemEntityById(itemId)
        );
    }

    @Transactional(readOnly = true)
    public List<Get> getItems(Pageable pageable) {
        return itemJpaRepository.findAll(pageable).stream()
            .map(ItemResponseDto.Get::fromEntity)
            .toList();
    }

    @Transactional
    public ItemResponseDto.Get createItem(ItemRequestDto.Post dto) {
        BrandEntity brandEntity = brandJpaRepository.findById(dto.brandId())
            .orElseThrow(() -> new NotFoundException(BrandEntity.class, dto.brandId()));

        CategoryEntity categoryEntity = categoryJpaRepository.findById(dto.categoryId())
            .orElseThrow(() -> new NotFoundException(CategoryEntity.class, dto.categoryId()));

        ItemEntity itemEntity = ItemEntity.builder()
            .brand(brandEntity)
            .category(categoryEntity)
            .price(dto.price())
            .name(dto.name())
            .build();

        return ItemResponseDto.Get.fromEntity(
            itemJpaRepository.save(itemEntity)
        );
    }

    @Transactional
    public void deleteItemById(Long itemId) {
        itemJpaRepository.findById(itemId).ifPresentOrElse(
            itemJpaRepository::delete,
            () -> {
                throw new NotFoundException(ItemEntity.class, itemId);
            }
        );
    }

    @Transactional
    public ItemResponseDto.Get updateItem(Long itemId, ItemRequestDto.Patch dto) {
        ItemEntity itemEntity = findItemEntityById(itemId);

        Optional.ofNullable(dto.brandId())
            .flatMap(brandJpaRepository::findById)
            .ifPresent(itemEntity::changeBrand);

        Optional.ofNullable(dto.categoryId())
            .flatMap(categoryJpaRepository::findById)
            .ifPresent(itemEntity::changeCategory);

        itemEntity.changeName(dto.name());
        itemEntity.changePrice(dto.price());

        return ItemResponseDto.Get.fromEntity(
            itemEntity
        );
    }

    @Transactional
    public ItemResponseDto.Get updateItem(Long itemId, ItemRequestDto.Put dto) {
        ItemEntity itemEntity = findItemEntityById(itemId);

        Preconditions.checkArgument(dto.name() != null, "Name must not be null");
        Preconditions.checkArgument(dto.price() != null, "Price must not be null");

        BrandEntity brandEntity = brandJpaRepository.findById(dto.brandId())
            .orElseThrow(() -> new NotFoundException(BrandEntity.class, dto.brandId()));

        CategoryEntity categoryEntity = categoryJpaRepository.findById(dto.categoryId())
            .orElseThrow(() -> new NotFoundException(CategoryEntity.class, dto.categoryId()));

        itemEntity.changeBrand(brandEntity);
        itemEntity.changeCategory(categoryEntity);
        itemEntity.changeName(dto.name());
        itemEntity.changePrice(dto.price());

        return ItemResponseDto.Get.fromEntity(
            itemEntity
        );
    }

    private ItemEntity findItemEntityById(Long itemId) {
        return itemJpaRepository.findById(itemId)
            .orElseThrow(() -> new NotFoundException(ItemEntity.class, itemId));
    }
}

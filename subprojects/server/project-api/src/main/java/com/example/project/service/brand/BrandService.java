package com.example.project.service.brand;

import com.example.project.controller.brand.dto.BrandRequestDto;
import com.example.project.controller.brand.dto.BrandResponseDto;
import com.example.project.controller.brand.dto.BrandResponseDto.Get;
import com.example.project.exception.NotFoundException;
import com.example.project.repository.jpa.entity.brand.BrandEntity;
import com.example.project.repository.jpa.repository.brand.BrandJpaRepository;
import com.example.project.repository.jpa.repository.item.ItemJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandJpaRepository brandJpaRepository;

    private final ItemJpaRepository itemJpaRepository;

    @Transactional(readOnly = true)
    public Page<Get> getBrands(Pageable pageable) {
        return brandJpaRepository.findAll(pageable)
            .map(BrandResponseDto.Get::fromEntity);
    }
    @Transactional(readOnly = true)
    public List<Get> getAllBrands() {
        return brandJpaRepository.findAll().stream()
            .map(BrandResponseDto.Get::fromEntity)
            .toList();
    }

    @Transactional(readOnly = true)
    public Get getBrandById(Long brandId) {
        return BrandResponseDto.Get.fromEntity(
            brandJpaRepository.findById(brandId)
                .orElseThrow(() -> new NotFoundException(BrandEntity.class, brandId))
        );
    }

    @Transactional
    public BrandResponseDto.Get createBrand(BrandRequestDto.Post dto) {
        return BrandResponseDto.Get.fromEntity(
            brandJpaRepository.save(
                BrandEntity.builder()
                    .name(dto.name())
                    .build()
            )
        );
    }

    @Transactional
    public BrandResponseDto.Get updateBrand(Long brandId, BrandRequestDto.Put dto) {
        BrandEntity brandEntity = brandJpaRepository.findById(brandId)
            .orElseThrow(() -> new NotFoundException(BrandEntity.class, brandId));

        brandEntity.changeName(dto.name());

        return BrandResponseDto.Get.fromEntity(brandEntity);
    }

    @Transactional
    public BrandResponseDto.Get updateBrand(Long brandId, BrandRequestDto.Patch dto) {
        BrandEntity brandEntity = brandJpaRepository.findById(brandId)
            .orElseThrow(() -> new NotFoundException(BrandEntity.class, brandId));

        brandEntity.changeName(dto.name());

        return BrandResponseDto.Get.fromEntity(brandEntity);
    }

    @Transactional
    public void deleteBrandById(Long brandId) {
        BrandEntity brandEntity = brandJpaRepository.findById(brandId)
            .orElseThrow(() -> new NotFoundException(BrandEntity.class, brandId));

        itemJpaRepository.setNoBrandByBrandId(brandId);

        brandJpaRepository.delete(brandEntity);
    }
}

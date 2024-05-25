package com.example.project.controller.brand;

import com.example.project.controller.brand.dto.BrandResponseDto;
import com.example.project.repository.jpa.repository.brand.BrandJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/brands")
public class BrandController {
    private final BrandJpaRepository brandJpaRepository;

    @GetMapping
    public List<BrandResponseDto.Get> getBrands(Pageable pageable) {
        return brandJpaRepository.findAll(pageable).stream()
            .map(BrandResponseDto.Get::fromEntity)
            .toList();
    }

    @GetMapping("/{brandId}")
    public BrandResponseDto.Get getBrandById(@PathVariable("brandId") Long brandId) {
        return BrandResponseDto.Get.fromEntity(
            brandJpaRepository.findById(brandId).orElseThrow()
        );
    }
}

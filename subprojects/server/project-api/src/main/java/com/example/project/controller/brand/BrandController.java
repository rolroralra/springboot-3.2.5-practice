package com.example.project.controller.brand;

import com.example.project.controller.brand.dto.BrandRequestDto;
import com.example.project.controller.brand.dto.BrandResponseDto;
import com.example.project.controller.brand.dto.BrandResponseDto.Get;
import com.example.project.service.brand.BrandService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/brands")
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    public Page<Get> getBrands(
        @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return brandService.getBrands(pageable);
    }

    @GetMapping("/all")
    public List<Get> getBrands() {
        return brandService.getAllBrands();
    }

    @GetMapping("/{brandId}")
    public BrandResponseDto.Get getBrandById(@PathVariable("brandId") Long brandId) {
        return brandService.getBrandById(brandId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BrandResponseDto.Get createBrand(@RequestBody BrandRequestDto.Post dto) {
        return brandService.createBrand(dto);
    }

    @PutMapping("/{brandId}")
    public BrandResponseDto.Get updateBrand(
        @PathVariable("brandId") Long brandId,
        @RequestBody BrandRequestDto.Put dto) {
        return brandService.updateBrand(brandId, dto);
    }

    @PatchMapping("/{brandId}")
    public BrandResponseDto.Get updateBrand(
        @PathVariable("brandId") Long brandId,
        @RequestBody BrandRequestDto.Patch dto) {
        return brandService.updateBrand(brandId, dto);
    }

    @DeleteMapping("/{brandId}")
    public void deleteBrand(@PathVariable("brandId") Long brandId) {
        brandService.deleteBrandById(brandId);
    }
}

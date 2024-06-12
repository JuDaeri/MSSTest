package com.musinsa.api.controller;

import com.musinsa.api.dto.*;
import com.musinsa.api.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/brand")
public class BrandController {

    private final BrandService brandService;

    @GetMapping("/lowest-total-price")
    @Operation(summary = "단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API")
    public List<BrandLowestPricedItemResp> getLowestTotalPrice() {
        return brandService.findLowestPricedByBrandName();
    }

    @PostMapping
    @Operation(summary = "브랜드 추가 API")
    public BrandAddResp addBrand(@RequestBody @Validated BrandAddReq brandAddReq) {
        return brandService.addBrand(brandAddReq.getBrandName());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "브랜드 삭제 API")
    public void deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "브랜드 수정 API")
    public void updateBrand(@PathVariable Long id, @RequestBody BrandUpdateReq brandUpdateReq) {
        brandService.updateBrand(id, brandUpdateReq);
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 ID의 브랜드 조회하는 API")
    public BrandFindResp.BrandDto getLowestTotalPrice(@PathVariable Long id) {
        return brandService.findByBrandId(id);
    }
}

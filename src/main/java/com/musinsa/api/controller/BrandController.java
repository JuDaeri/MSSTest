package com.musinsa.api.controller;

import com.musinsa.api.dto.BrandAddReq;
import com.musinsa.api.dto.BrandAddResp;
import com.musinsa.api.dto.BrandLowestPricedItemResp;
import com.musinsa.api.service.BrandService;
import com.musinsa.api.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/brand")
public class BrandController {

    private final ProductService productService;
    private final BrandService brandService;

    @GetMapping("/lowest-total-price")
    @Operation(summary = "단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API")
    public List<BrandLowestPricedItemResp> getLowestTotalPrice() {
        return productService.findLowestPricedByBrandName();
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
}

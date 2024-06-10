package com.musinsa.api.controller;

import com.musinsa.api.dto.CategoryLowestPricedItemResp;
import com.musinsa.api.dto.PriceRangeByCategoryNameResp;
import com.musinsa.api.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final ProductService productService;

    @GetMapping("/lowest-price")
    @Operation(summary = "카테고리 별 최저가격 브랜드와 상품가격, 총액을 조회하는 API")
    public CategoryLowestPricedItemResp getLowestPrice() {
        CategoryLowestPricedItemResp categoryLowestPricedItemResp = productService.findLowestPriceByCategory();
        return categoryLowestPricedItemResp;
    }

    @GetMapping("/{categoryName}/price-range")
    @Operation(summary = "카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API")
    public PriceRangeByCategoryNameResp getPriceRange(@PathVariable String categoryName) {
        PriceRangeByCategoryNameResp lowestPriceByCategoryName = productService.findLowestPriceByCategoryName(categoryName);
        return lowestPriceByCategoryName;
    }
}

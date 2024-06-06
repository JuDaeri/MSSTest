package com.musinsa.api.controller;

import com.musinsa.api.dto.CategoryLowestPricedItemResp;
import com.musinsa.api.dto.PriceRangeByCategoryNameResp;
import com.musinsa.api.service.ProductService;
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
    public CategoryLowestPricedItemResp getLowestPrice() {
        CategoryLowestPricedItemResp categoryLowestPricedItemResp = productService.findLowestPriceByCategory();
        return categoryLowestPricedItemResp;
    }

    @GetMapping("/{categoryName}/price-range")
    public PriceRangeByCategoryNameResp getPriceRange(@PathVariable String categoryName) {
        PriceRangeByCategoryNameResp lowestPriceByCategoryName = productService.findLowestPriceByCategoryName(categoryName);
        return lowestPriceByCategoryName;
    }
}

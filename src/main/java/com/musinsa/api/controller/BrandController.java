package com.musinsa.api.controller;

import com.musinsa.api.dto.BrandAddReq;
import com.musinsa.api.dto.BrandAddResp;
import com.musinsa.api.dto.BrandLowestPricedItemResp;
import com.musinsa.api.service.BrandService;
import com.musinsa.api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/brand")
public class BrandController {

    private final ProductService productService;
    private final BrandService brandService;

    @GetMapping("/lowest-total-price")
    public List<BrandLowestPricedItemResp> getLowestTotalPrice() {
        return productService.findLowestPricedByBrandName();
    }

    @PostMapping
    public BrandAddResp addBrand(@RequestBody BrandAddReq brandAddReq) {
        return brandService.addBrand(brandAddReq.getBrandName());
    }
}

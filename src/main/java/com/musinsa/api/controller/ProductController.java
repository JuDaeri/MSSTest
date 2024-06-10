package com.musinsa.api.controller;

import com.musinsa.api.dto.ProductAddReq;
import com.musinsa.api.dto.ProductAddResp;
import com.musinsa.api.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "상품 추가 API")
    public ProductAddResp addProduct(@RequestBody @Validated ProductAddReq productAddReq) {
        return productService.addProduct(productAddReq.getBrandId(),
                productAddReq.getCategoryId(), productAddReq.getPrice());
    }

    @DeleteMapping("{id}")
    @Operation(summary = "상품 삭제 API")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}

package com.musinsa.api.controller;

import com.musinsa.api.dto.*;
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

    @PutMapping("/{id}")
    @Operation(summary = "상품 수정 API")
    public void updateProduct(@PathVariable Long id, @RequestBody ProductUpdateReq productUpdateReq) {
        productService.updateProduct(id, productUpdateReq);
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 ID의 상품을 조회하는 API")
    public ProductFindResp.ProductDto getLowestTotalPrice(@PathVariable Long id) {
        return productService.findByProductId(id);
    }
}

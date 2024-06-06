package com.musinsa.api.controller;

import com.musinsa.api.dto.ProductAddReq;
import com.musinsa.api.dto.ProductAddResp;
import com.musinsa.api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductAddResp addProduct(@RequestBody ProductAddReq productAddReq) {
        return productService.addProduct(productAddReq.getBrandId(),
                productAddReq.getCategoryId(), productAddReq.getPrice());
    }
}

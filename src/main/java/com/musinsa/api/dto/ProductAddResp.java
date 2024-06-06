package com.musinsa.api.dto;

import lombok.Getter;

@Getter
public class ProductAddResp {
    Long productId;

    public ProductAddResp(Long productId) {
        this.productId = productId;
    }
}

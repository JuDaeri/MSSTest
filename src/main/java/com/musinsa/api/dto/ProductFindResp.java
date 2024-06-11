package com.musinsa.api.dto;

import com.musinsa.api.domain.Product;
import lombok.Getter;

public class ProductFindResp {

    @Getter
    public static class ProductDto {
        private Long productId;
        private Integer price;
        private Long categoryId;
        private Long brandId;

        public ProductDto(Product product) {
            this.productId = product.getProductId();
            this.price = product.getPrice();
            this.categoryId = product.getCategory() != null ? product.getCategory().getCategoryId() : null;
            this.brandId = product.getBrand() != null ? product.getBrand().getBrandId() : null;
        }
    }
}

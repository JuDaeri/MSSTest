package com.musinsa.api.dto;

import com.musinsa.api.domain.Product;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class BrandLowestPricedItemResp {
    private String brandName;
    private List<CategoryDto> categoryDtos;
    private Integer totalPrice;

    public BrandLowestPricedItemResp(String brandName, List<CategoryDto> categoryDtos, Integer totalPrice) {
        this.brandName = brandName;
        this.categoryDtos = categoryDtos;
        this.totalPrice = totalPrice;
    }

    @Getter
    @ToString
    public static class CategoryDto {
        private String categoryName;
        private Integer price;

        public CategoryDto(Product product) {
            this.categoryName = product.getCategory().getCategoryName();
            this.price = product.getPrice();
        }
    }
}

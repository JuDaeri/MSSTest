package com.musinsa.api.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CategoryLowestPricedItemDto {
    private String categoryName;
    private String brandName;
    private Integer lowestPrice;

    public CategoryLowestPricedItemDto(String categoryName, String brandName, Integer lowestPrice) {
        this.categoryName = categoryName;
        this.brandName = brandName;
        this.lowestPrice = lowestPrice;
    }
}

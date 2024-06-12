package com.musinsa.api.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CategoryLowestPricedItemResp {

    private Integer totalPrice;

    private List<CategoryLowestPricedItemDto> categoryLowestPricedItemDtos;

    public CategoryLowestPricedItemResp(Integer totalPrice, List<CategoryLowestPricedItemDto> categoryLowestPricedItemDtos) {
        this.totalPrice = totalPrice;
        this.categoryLowestPricedItemDtos = categoryLowestPricedItemDtos;
    }
}

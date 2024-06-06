package com.musinsa.api.dto;

import com.musinsa.api.domain.Product;
import lombok.Getter;

@Getter
public class PriceRangeByCategoryNameResp {

    private String categoryName;
    private ItemDto lowestItem;
    private ItemDto highestItem;

    public PriceRangeByCategoryNameResp(String categoryName, ItemDto lowestItem, ItemDto highestItem) {
        this.categoryName = categoryName;
        this.lowestItem = lowestItem;
        this.highestItem = highestItem;
    }

    @Getter
    public static class ItemDto {
        private String brandName;
        private Integer price;

        public ItemDto(Product product) {
            this.brandName = product.getBrand().getBrandName();
            this.price = product.getPrice();
        }
    }
}

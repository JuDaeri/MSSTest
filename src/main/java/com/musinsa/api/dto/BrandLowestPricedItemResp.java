package com.musinsa.api.dto;

import com.musinsa.api.domain.Product;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class BrandLowestPricedItemResp {
    private String brandName;
    private List<ItemDto> itemDtos;
    private Integer totalPrice;

    public BrandLowestPricedItemResp(String brandName, List<ItemDto> itemDtos, Integer totalPrice) {
        this.brandName = brandName;
        this.itemDtos = itemDtos;
        this.totalPrice = totalPrice;
    }

    @Getter
    @ToString
    public static class ItemDto {
        private String categoryName;
        private Integer price;

        public ItemDto(Product product) {
            this.categoryName = product.getCategory().getCategoryName();
            this.price = product.getPrice();
        }
    }
}

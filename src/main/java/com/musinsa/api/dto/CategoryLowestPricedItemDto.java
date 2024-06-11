package com.musinsa.api.dto;

import com.musinsa.api.domain.Product;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class CategoryLowestPricedItemDto {
    private String categoryName;
    private String brandName;
    private Integer lowestPrice;

    public CategoryLowestPricedItemDto(Product a) {
        this.categoryName = a.getCategory().getCategoryName();
        this.brandName = a.getBrand().getBrandName();
        this.lowestPrice = a.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryLowestPricedItemDto that = (CategoryLowestPricedItemDto) o;
        return Objects.equals(categoryName, that.categoryName) && Objects.equals(lowestPrice, that.lowestPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryName, lowestPrice);
    }
}

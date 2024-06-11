package com.musinsa.api.dto;


import com.musinsa.api.domain.Brand;
import lombok.Getter;

public class BrandFindResp {

    @Getter
    public static class BrandDto {
        private String brandName;

        public BrandDto(Brand brand) {
            this.brandName = brand.getBrandName();
        }
    }
}

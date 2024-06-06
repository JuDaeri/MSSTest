package com.musinsa.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandAddResp {
    private Long brandId;

    public BrandAddResp(Long brandId) {
        this.brandId = brandId;
    }
}

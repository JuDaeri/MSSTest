package com.musinsa.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAddReq {
    private Long categoryId;
    private Long brandId;
    private Integer price;
}

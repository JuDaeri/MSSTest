package com.musinsa.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateReq {

    @NotNull(message = "카테고리ID를 입력해주세요")
    private Long categoryId;

    @NotNull(message = "브랜드ID를 입력해주세요")
    private Long brandId;

    @NotNull(message = "가격을 입력해주세요")
    private Integer price;
}

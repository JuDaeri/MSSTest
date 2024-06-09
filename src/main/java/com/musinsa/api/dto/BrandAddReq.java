package com.musinsa.api.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandAddReq {
    @NotEmpty(message = "브랜드명을 입력해주세요")
    private String brandName;
}

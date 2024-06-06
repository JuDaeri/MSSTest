package com.musinsa.api.service;

import com.musinsa.api.domain.Brand;
import com.musinsa.api.dto.BrandAddResp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
@SpringBootTest
class BrandServiceTest {

    @Autowired
    BrandService brandService;

    @Test
    @DisplayName("브랜드등록")
    void addBrandTest_success() {
        BrandAddResp brandAddResp = brandService.addBrand("A");
        assertNotNull(brandAddResp.getBrandId());
    }
}
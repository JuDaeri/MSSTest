package com.musinsa.api.service;

import com.musinsa.api.domain.Brand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class BrandServiceTest {

    @Autowired
    BrandService brandService;

    @Test
    @DisplayName("브랜드등록")
    void addBrandTest_success() {
        Brand brand = brandService.addBrand("A");
        Assertions.assertNotNull(brand.getBrandId());
    }
}
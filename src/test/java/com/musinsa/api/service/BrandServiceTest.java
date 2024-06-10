package com.musinsa.api.service;

import com.musinsa.api.domain.Brand;
import com.musinsa.api.dto.BrandAddResp;
import com.musinsa.api.repository.BrandRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class BrandServiceTest {

    @Autowired
    BrandService brandService;

    @Autowired
    BrandRepository brandRepository;

    @Test
    @DisplayName("브랜드등록")
    void addBrandTest_success() {
        BrandAddResp brandAddResp = brandService.addBrand("A");
        assertNotNull(brandAddResp.getBrandId());
    }

    @Test
    @DisplayName("브랜드 삭제")
    void deleteBrandTest_success() {
        Optional<Brand> brand = brandRepository.findById(1L);
        assertTrue(brand.isPresent());

        brandService.deleteBrand(1l);
        Optional<Brand> deletedBrand = brandRepository.findById(1L);
        assertFalse(deletedBrand.isPresent());
    }
}
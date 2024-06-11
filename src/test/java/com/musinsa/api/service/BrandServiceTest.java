package com.musinsa.api.service;

import com.musinsa.api.domain.Brand;
import com.musinsa.api.dto.BrandAddResp;
import com.musinsa.api.dto.BrandFindResp;
import com.musinsa.api.dto.BrandUpdateReq;
import com.musinsa.api.repository.BrandRepository;
import jakarta.persistence.EntityNotFoundException;
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

    @Test
    @DisplayName("브랜드등록")
    void addBrandTest_success() {
        BrandAddResp brandAddResp = brandService.addBrand("A");
        assertNotNull(brandAddResp.getBrandId());
    }

    @Test
    @DisplayName("브랜드 삭제")
    void deleteBrandTest_success() {
        BrandFindResp.BrandDto brandDto = brandService.findByBrandId(1L);
        assertNotNull(brandDto);

        brandService.deleteBrand(1l);
        assertThrows(EntityNotFoundException.class, () -> brandService.findByBrandId(1L));
    }

    @Test
    @DisplayName("브랜드 수정")
    void updateBrandTest_success() {
        BrandFindResp.BrandDto brandDto = brandService.findByBrandId(1L);
        assertEquals("A", brandDto.getBrandName());

        BrandUpdateReq brandUpdateReq = new BrandUpdateReq();
        brandUpdateReq.setBrandName("newbrand");
        brandService.updateBrand(1l, brandUpdateReq);

        BrandFindResp.BrandDto updatedBrandDto = brandService.findByBrandId(1L);
        assertEquals("newbrand", updatedBrandDto.getBrandName());
    }
}
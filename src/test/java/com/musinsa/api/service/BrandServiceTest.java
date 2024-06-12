package com.musinsa.api.service;

import com.musinsa.api.dto.BrandAddResp;
import com.musinsa.api.dto.BrandFindResp;
import com.musinsa.api.dto.BrandLowestPricedItemResp;
import com.musinsa.api.dto.BrandUpdateReq;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
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

    @Test
    @DisplayName("단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회")
    void findLowestPriceByBrandTest() {
        List<BrandLowestPricedItemResp> brandLowestPricedItemResps = brandService.findLowestPricedByBrandName();
        assertEquals(brandLowestPricedItemResps.size(), 1);

        BrandLowestPricedItemResp brandLowestPricedItemResp = brandLowestPricedItemResps.get(0);
        assertEquals("D", brandLowestPricedItemResp.getBrandName());
        assertEquals(36_100, brandLowestPricedItemResp.getTotalPrice());
        assertEquals(8, brandLowestPricedItemResp.getItemDtos().size());
    }
}
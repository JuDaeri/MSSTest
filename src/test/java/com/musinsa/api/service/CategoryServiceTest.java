package com.musinsa.api.service;

import com.musinsa.api.dto.CategoryLowestPricedItemResp;
import com.musinsa.api.dto.PriceRangeByCategoryNameResp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @Test
    @DisplayName("카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회")
    void findLowestPriceByCategoryTest() {
        CategoryLowestPricedItemResp categoryLowestPricedItemResp = categoryService.findLowestPriceByCategory();

        // 구현1 예시에서 34,100원으로 되어있으나, 스니커즈 최저가가 9,000원인 건이 2개(A, G)이고
        // 가격이 같을 경우에 어떻게 처리할지에 대한 예시가 없어서 총액 43,100원으로 하였음.
        assertEquals(34_100, categoryLowestPricedItemResp.getTotalPrice());
    }

    @Test
    @DisplayName("카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회")
    void findLowestPriceByCategoryNameTest() {
        PriceRangeByCategoryNameResp priceRangeByCategoryNameResp = categoryService.findLowestPriceByCategoryName("상의");
        assertEquals("상의", priceRangeByCategoryNameResp.getCategoryName());

        PriceRangeByCategoryNameResp.ItemDto lowestItem = priceRangeByCategoryNameResp.getLowestItem();
        assertEquals(10_000, lowestItem.getPrice());
        assertEquals("C", lowestItem.getBrandName());

        PriceRangeByCategoryNameResp.ItemDto highestItem = priceRangeByCategoryNameResp.getHighestItem();
        assertEquals(11_400, highestItem.getPrice());
        assertEquals("I", highestItem.getBrandName());
    }

}
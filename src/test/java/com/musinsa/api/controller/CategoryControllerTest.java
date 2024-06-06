package com.musinsa.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회")
    void getLowestPriceTest() throws Exception {
        String expectedContent = "{\"totalPrice\":43100,\"categoryLowestPricedItemDtos\":[{\"categoryName\":\"상의\",\"brandName\":\"C\",\"lowestPrice\":10000},{\"categoryName\":\"아우터\",\"brandName\":\"E\",\"lowestPrice\":5000},{\"categoryName\":\"바지\",\"brandName\":\"D\",\"lowestPrice\":3000},{\"categoryName\":\"스니커즈\",\"brandName\":\"A\",\"lowestPrice\":9000},{\"categoryName\":\"스니커즈\",\"brandName\":\"G\",\"lowestPrice\":9000},{\"categoryName\":\"가방\",\"brandName\":\"A\",\"lowestPrice\":2000},{\"categoryName\":\"모자\",\"brandName\":\"D\",\"lowestPrice\":1500},{\"categoryName\":\"양말\",\"brandName\":\"I\",\"lowestPrice\":1700},{\"categoryName\":\"악세서리\",\"brandName\":\"F\",\"lowestPrice\":1900}]}";
        mockMvc.perform(get("/api/v1/category/lowest-price"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));
    }

    @Test
    @DisplayName("카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회")
    void getPriceRangeTest() throws Exception {

        String expectedContent = "{\"categoryName\":\"상의\",\"lowestItem\":{\"brandName\":\"C\",\"price\":10000},\"highestItem\":{\"brandName\":\"I\",\"price\":11400}}";

        mockMvc.perform(get("/api/v1/category/상의/price-range"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));
    }

    @Test
    @DisplayName("카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회 실패")
    void getPriceRangeTest_FAIL() throws Exception {
        mockMvc.perform(get("/api/v1/category/한복/price-range"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("한복(이)라는 카테고리명은 존재하지 않습니다."));
    }
}
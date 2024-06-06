package com.musinsa.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.api.dto.BrandAddReq;
import com.musinsa.api.dto.ProductAddReq;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회")
    void getLowestTotalPriceTest() throws Exception {
        String expectedContent = "[{\"brandName\":\"D\",\"itemDtos\":[{\"categoryName\":\"상의\",\"price\":10100},{\"categoryName\":\"아우터\",\"price\":5100},{\"categoryName\":\"바지\",\"price\":3000},{\"categoryName\":\"스니커즈\",\"price\":9500},{\"categoryName\":\"가방\",\"price\":2500},{\"categoryName\":\"모자\",\"price\":1500},{\"categoryName\":\"양말\",\"price\":2400},{\"categoryName\":\"악세서리\",\"price\":2000}],\"totalPrice\":36100}]";

        mockMvc.perform(get("/api/v1/brand/lowest-total-price"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));
    }

    @Test
    @DisplayName("브랜드 추가")
    void addBrandTest() throws Exception {
        BrandAddReq brandAddReq = new BrandAddReq();
        brandAddReq.setBrandName("Test");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(brandAddReq);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("brandId")));
    }
}
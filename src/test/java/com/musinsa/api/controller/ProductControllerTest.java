package com.musinsa.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.api.dto.BrandUpdateReq;
import com.musinsa.api.dto.ProductAddReq;
import com.musinsa.api.dto.ProductUpdateReq;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("상품 추가")
    void addProductTest() throws Exception {
        ProductAddReq productAddReq = new ProductAddReq();
        productAddReq.setCategoryId(1l);
        productAddReq.setBrandId(3l);
        productAddReq.setPrice(300);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productAddReq);

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("productId")));
    }

    @Test
    @DisplayName("상품 추가 카테고리ID 생략시")
    void addProductTest_Error1() throws Exception {
        ProductAddReq productAddReq = new ProductAddReq();
        productAddReq.setCategoryId(null);
        productAddReq.setBrandId(3l);
        productAddReq.setPrice(300);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productAddReq);

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("카테고리ID를 입력해주세요")));
    }

    @Test
    @DisplayName("상품 추가 브랜드ID 생략시")
    void addProductTest_Error2() throws Exception {
        ProductAddReq productAddReq = new ProductAddReq();
        productAddReq.setCategoryId(1l);
        productAddReq.setBrandId(null);
        productAddReq.setPrice(300);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productAddReq);

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("브랜드ID를 입력해주세요")));
    }

    @Test
    @DisplayName("상품 추가 가격정보 생략시")
    void addProductTest_Error3() throws Exception {
        ProductAddReq productAddReq = new ProductAddReq();
        productAddReq.setCategoryId(1l);
        productAddReq.setBrandId(3l);
        productAddReq.setPrice(null);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productAddReq);

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(Matchers.containsString("가격을 입력해주세요")));
    }

    @Test
    @DisplayName("상품 삭제")
    void deleteProductTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/product/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 수정")
    void updateProductTest() throws Exception {

        ProductUpdateReq productUpdateReq = new ProductUpdateReq();
        productUpdateReq.setPrice(5000);
        productUpdateReq.setCategoryId(2l);
        productUpdateReq.setBrandId(2l);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productUpdateReq);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk());
    }
}
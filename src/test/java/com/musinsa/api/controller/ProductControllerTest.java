package com.musinsa.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.api.dto.ProductAddReq;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
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
}
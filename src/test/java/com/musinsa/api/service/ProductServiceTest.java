package com.musinsa.api.service;

import com.musinsa.api.domain.Brand;
import com.musinsa.api.domain.Category;
import com.musinsa.api.dto.ProductAddResp;
import com.musinsa.api.dto.ProductFindResp;
import com.musinsa.api.dto.ProductUpdateReq;
import com.musinsa.api.repository.BrandRepository;
import com.musinsa.api.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductService productService;

    @Test
    @DisplayName("상품등록")
    void addProductTest_success() {
        Brand brand = brandRepository.save(new Brand("A"));
        Category category = categoryRepository.save(new Category("상의"));
        ProductAddResp productAddResp = productService.addProduct(brand.getBrandId(), category.getCategoryId(), 1_000);

        assertNotNull(productAddResp.getProductId());
    }

    @Test
    @DisplayName("상품등록시 브랜드가 없을경우")
    void addProductTest_fail1() {
        Category category = categoryRepository.save(new Category("상의"));
        assertThrows(EntityNotFoundException.class,
                () -> productService.addProduct(Long.MAX_VALUE, category.getCategoryId(), 1_000));
    }

    @Test
    @DisplayName("상품등록시 카테고리가 없을경우")
    void addProductTest_fail2() {
        Brand brand = brandRepository.save(new Brand("A"));

        assertThrows(EntityNotFoundException.class,
                () -> productService.addProduct(brand.getBrandId(), Long.MAX_VALUE, 1_000));
    }

    @Test
    @DisplayName("상품 삭제")
    void deleteProductTest_success() {
        ProductFindResp.ProductDto productDto = productService.findByProductId(1L);
        assertNotNull(productDto);

        productService.deleteProduct(1l);

        assertThrows(EntityNotFoundException.class, () -> productService.findByProductId(1L));
    }

    @Test
    @DisplayName("상품 수정")
    void updateProductTest_success() {
        ProductFindResp.ProductDto productDto = productService.findByProductId(1L);
        assertEquals(11200, productDto.getPrice());
        assertEquals(1l, productDto.getCategoryId());
        assertEquals(1l, productDto.getBrandId());

        ProductUpdateReq productUpdateReq = new ProductUpdateReq();
        productUpdateReq.setPrice(5000);
        productUpdateReq.setCategoryId(2l);
        productUpdateReq.setBrandId(2l);

        productService.updateProduct(1l, productUpdateReq);
        ProductFindResp.ProductDto updatedProductDto = productService.findByProductId(1L);
        assertEquals(5000, updatedProductDto.getPrice());
        assertEquals(2l, updatedProductDto.getCategoryId());
        assertEquals(2l, updatedProductDto.getBrandId());
    }
}
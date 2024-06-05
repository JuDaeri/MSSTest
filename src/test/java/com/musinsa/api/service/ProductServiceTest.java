package com.musinsa.api.service;

import com.musinsa.api.domain.Brand;
import com.musinsa.api.domain.Category;
import com.musinsa.api.domain.Product;
import com.musinsa.api.repository.BrandRepository;
import com.musinsa.api.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
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
        Product product = productService.addProduct(brand.getBrandId(), category.getCategoryId(), 1_000);

        Assertions.assertNotNull(product.getProductId());
    }

    @Test
    @DisplayName("상품등록시 브랜드가 없을경우")
    void addProductTest_fail1() {
        Category category = categoryRepository.save(new Category("상의"));
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> productService.addProduct(Long.MAX_VALUE, category.getCategoryId(), 1_000));
    }

    @Test
    @DisplayName("상품등록시 카테고리가 없을경우")
    void addProductTest_fail2() {
        Brand brand = brandRepository.save(new Brand("A"));

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> productService.addProduct(brand.getBrandId(), Long.MAX_VALUE, 1_000));
    }
}
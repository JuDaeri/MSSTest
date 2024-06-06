package com.musinsa.api.service;

import com.musinsa.api.domain.Brand;
import com.musinsa.api.domain.Category;
import com.musinsa.api.domain.Product;
import com.musinsa.api.dto.BrandLowestPricedItemResp;
import com.musinsa.api.dto.CategoryLowestPricedItemResp;
import com.musinsa.api.dto.PriceRangeByCategoryNameResp;
import com.musinsa.api.repository.BrandRepository;
import com.musinsa.api.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Test
    @DisplayName("카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회")
    void findLowestPriceByCategoryTest() {
        CategoryLowestPricedItemResp categoryLowestPricedItemResp = productService.findLowestPriceByCategory();

        // 구현1 예시에서 34,100원으로 되어있으나, 스니커즈 최저가가 9,000원인 건이 2개(A, G)이고
        // 가격이 같을 경우에 어떻게 처리할지에 대한 예시가 없어서 총액 43,100원으로 하였음.
        Assertions.assertEquals(43_100, categoryLowestPricedItemResp.getTotalPrice());
    }

    @Test
    @DisplayName("단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회")
    void findLowestPriceByBrandTest() {
        List<BrandLowestPricedItemResp> brandLowestPricedItemResps = productService.findLowestPricedByBrandName();
        Assertions.assertEquals(brandLowestPricedItemResps.size(), 1);

        BrandLowestPricedItemResp brandLowestPricedItemResp = brandLowestPricedItemResps.get(0);
        Assertions.assertEquals("D", brandLowestPricedItemResp.getBrandName());
        Assertions.assertEquals(36_100, brandLowestPricedItemResp.getTotalPrice());
        Assertions.assertEquals(8, brandLowestPricedItemResp.getItemDtos().size());
    }

    @Test
    @DisplayName("카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회")
    void findLowestPriceByCategoryNameTest() {
        PriceRangeByCategoryNameResp priceRangeByCategoryNameResp = productService.findLowestPriceByCategoryName("상의");
        Assertions.assertEquals("상의", priceRangeByCategoryNameResp.getCategoryName());

        PriceRangeByCategoryNameResp.ItemDto lowestItem = priceRangeByCategoryNameResp.getLowestItem();
        Assertions.assertEquals(10_000, lowestItem.getPrice());
        Assertions.assertEquals("C", lowestItem.getBrandName());

        PriceRangeByCategoryNameResp.ItemDto highestItem = priceRangeByCategoryNameResp.getHighestItem();
        Assertions.assertEquals(11_400, highestItem.getPrice());
        Assertions.assertEquals("I", highestItem.getBrandName());
    }
}
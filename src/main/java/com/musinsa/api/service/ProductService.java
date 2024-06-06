package com.musinsa.api.service;

import com.musinsa.api.domain.Brand;
import com.musinsa.api.domain.Category;
import com.musinsa.api.domain.Product;
import com.musinsa.api.dto.BrandLowestPricedItemResp;
import com.musinsa.api.dto.CategoryLowestPricedItemDto;
import com.musinsa.api.dto.CategoryLowestPricedItemResp;
import com.musinsa.api.repository.BrandRepository;
import com.musinsa.api.repository.CategoryRepository;
import com.musinsa.api.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    // 상품 추가
    public Product addProduct(Long brandId, Long categoryId, Integer price) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 카테고리입니다."));

        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 브랜드입니다."));

        Product product = new Product(price, category, brand);
        return productRepository.save(product);
    }

    public CategoryLowestPricedItemResp findLowestPriceByCategoryName() {
        List<CategoryLowestPricedItemDto> categoryLowestPricedItemDtos = productRepository.findLowestPriceByCategoryName();
        int totalPrice = categoryLowestPricedItemDtos.stream().mapToInt(CategoryLowestPricedItemDto::getLowestPrice).sum();
        return new CategoryLowestPricedItemResp(totalPrice, categoryLowestPricedItemDtos);
    }

    public List<BrandLowestPricedItemResp> findLowestPricedByBrandName() {
        List<Brand> brands = productRepository.findLowestPriceByBrand();

        List<BrandLowestPricedItemResp> brandLowestPricedItemResps = new ArrayList<>();

        for (Brand brand : brands) {
            List<Product> products = brand.getProducts();
            int totalPrice = products.stream().mapToInt(Product::getPrice).sum();
            List<BrandLowestPricedItemResp.CategoryDto> categories = products.stream()
                    .map(product -> new BrandLowestPricedItemResp.CategoryDto(product)).toList();

            brandLowestPricedItemResps.add(new BrandLowestPricedItemResp(brand.getBrandName(), categories, totalPrice));
        }

        return brandLowestPricedItemResps;
    }
}

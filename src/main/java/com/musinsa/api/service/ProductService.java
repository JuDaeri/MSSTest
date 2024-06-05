package com.musinsa.api.service;

import com.musinsa.api.domain.Brand;
import com.musinsa.api.domain.Category;
import com.musinsa.api.domain.Product;
import com.musinsa.api.dto.LowestPriceDto;
import com.musinsa.api.repository.BrandRepository;
import com.musinsa.api.repository.CategoryRepository;
import com.musinsa.api.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<LowestPriceDto> findLowestPriceByCategoryName() {
        return productRepository.findLowestPriceByCategoryName();
    }
}

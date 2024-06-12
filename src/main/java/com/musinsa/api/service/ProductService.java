package com.musinsa.api.service;

import com.musinsa.api.domain.Brand;
import com.musinsa.api.domain.Category;
import com.musinsa.api.domain.Product;
import com.musinsa.api.dto.ProductAddResp;
import com.musinsa.api.dto.ProductFindResp;
import com.musinsa.api.dto.ProductUpdateReq;
import com.musinsa.api.repository.BrandRepository;
import com.musinsa.api.repository.CategoryRepository;
import com.musinsa.api.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    // 상품 추가
    public ProductAddResp addProduct(Long brandId, Long categoryId, Integer price) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 카테고리입니다. categoryId : " + categoryId));

        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 브랜드입니다. brandId : " + brandId));

        Product product = new Product(price, category, brand);
        productRepository.save(product);

        return new ProductAddResp(product.getProductId());
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void updateProduct(Long id, ProductUpdateReq productUpdateReq) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("상품이 존재하지 않습니다. productId : " + id));

        Category category = categoryRepository.findById(productUpdateReq.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 카테고리입니다. categoryId : " + productUpdateReq.getCategoryId()));
        Brand brand = brandRepository.findById(productUpdateReq.getBrandId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 브랜드입니다. brandId : " + productUpdateReq.getBrandId()));

        product.changePrice(productUpdateReq.getPrice());
        product.changeBrand(brand);
        product.changeCategory(category);
    }

    public ProductFindResp.ProductDto findByProductId(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("찾으려는 상품이 존재하지 않습니다. productId : " + id));
        return new ProductFindResp.ProductDto(product);
    }
}

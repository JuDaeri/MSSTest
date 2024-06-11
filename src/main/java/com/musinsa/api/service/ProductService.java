package com.musinsa.api.service;

import com.musinsa.api.domain.Brand;
import com.musinsa.api.domain.Category;
import com.musinsa.api.domain.Product;
import com.musinsa.api.dto.*;
import com.musinsa.api.repository.BrandRepository;
import com.musinsa.api.repository.CategoryRepository;
import com.musinsa.api.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 카테고리입니다."));

        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 브랜드입니다."));

        Product product = new Product(price, category, brand);
        productRepository.save(product);

        return new ProductAddResp(product.getProductId());
    }

    public CategoryLowestPricedItemResp findLowestPriceByCategory() {
        List<CategoryLowestPricedItemDto> categoryLowestPricedItemDtos = productRepository.findLowestPriceByCategoryName();
        int totalPrice = categoryLowestPricedItemDtos.stream().mapToInt(CategoryLowestPricedItemDto::getLowestPrice).sum();
        return new CategoryLowestPricedItemResp(totalPrice, categoryLowestPricedItemDtos);
    }

    public List<BrandLowestPricedItemResp> findLowestPricedByBrandName() {
        // 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드 검색
        List<Brand> brands = brandRepository.findLowestPriceByBrand();

        List<BrandLowestPricedItemResp> brandLowestPricedItemResps = new ArrayList<>();

        // 각 최저가 브랜드의 카테고리 및 총 가격 정보를 추출
        for (Brand brand : brands) {
            BrandLowestPricedItemResp e = getBrandLowestPricedItemResp(brand);
            brandLowestPricedItemResps.add(e);
        }

        return brandLowestPricedItemResps;
    }

    /**
     * 각 최저가 브랜드의 카테고리 및 총 가격 정보를 추출
     * @param brand
     * @return BrandLowestPricedItemResp
     */
    private BrandLowestPricedItemResp getBrandLowestPricedItemResp(Brand brand) {
        List<Product> products = productRepository.findAllByBrand(brand);

        int totalPrice = 0;
        List<BrandLowestPricedItemResp.ItemDto> categories = new ArrayList<>();

        for (Product product : products) {
            totalPrice += product.getPrice();
            categories.add(new BrandLowestPricedItemResp.ItemDto(product));
        }

        return new BrandLowestPricedItemResp(brand.getBrandName(), categories, totalPrice);
    }

    public PriceRangeByCategoryNameResp findLowestPriceByCategoryName(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new EntityNotFoundException(categoryName + "(이)라는 카테고리명은 존재하지 않습니다."));

        TreeSet<Product> products = new TreeSet<>(Comparator.comparingInt(Product::getPrice));
        products.addAll(category.getProducts());

        return new PriceRangeByCategoryNameResp(category.getCategoryName(),
                new PriceRangeByCategoryNameResp.ItemDto(products.first()), // 최저가 상품
                new PriceRangeByCategoryNameResp.ItemDto(products.last()) // 최고가 상품
        );
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void updateProduct(Long id, ProductUpdateReq productUpdateReq) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("상품이 존재하지 않습니다. id : " + id));

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
                .orElseThrow(() -> new EntityNotFoundException("찾으려는 상품이 존재하지 않습니다. id : " + id));
        return new ProductFindResp.ProductDto(product);
    }
}

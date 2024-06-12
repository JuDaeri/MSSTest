package com.musinsa.api.service;

import com.musinsa.api.domain.Category;
import com.musinsa.api.domain.Product;
import com.musinsa.api.dto.CategoryLowestPricedItemDto;
import com.musinsa.api.dto.CategoryLowestPricedItemResp;
import com.musinsa.api.dto.PriceRangeByCategoryNameResp;
import com.musinsa.api.repository.CategoryRepository;
import com.musinsa.api.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 별 최저가격 브랜드와 상품가격, 총액을 조회
     */
    public CategoryLowestPricedItemResp findLowestPriceByCategory() {
        List<CategoryLowestPricedItemDto> categoryLowestPricedItemDtos = productRepository.findLowestPriceByCategoryName()
                .stream()
                .map(CategoryLowestPricedItemDto::new)
                .sorted(Comparator.comparing(CategoryLowestPricedItemDto::getBrandName))
                .distinct()
                .collect(Collectors.toList());

        int totalPrice = categoryLowestPricedItemDtos.stream().mapToInt(CategoryLowestPricedItemDto::getLowestPrice).sum();
        return new CategoryLowestPricedItemResp(totalPrice, categoryLowestPricedItemDtos);
    }

    /**
     * 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회
     */
    public PriceRangeByCategoryNameResp findLowestPriceByCategoryName(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new EntityNotFoundException(categoryName + "(이)라는 카테고리명은 존재하지 않습니다."));
        List<Product> products = productRepository.findByCategoryOrderByPrice(category);

        return new PriceRangeByCategoryNameResp(category.getCategoryName(),
                new PriceRangeByCategoryNameResp.ItemDto(products.get(0)), // 최저가 상품
                new PriceRangeByCategoryNameResp.ItemDto(products.get(products.size() - 1)) // 최고가 상품
        );
    }
}

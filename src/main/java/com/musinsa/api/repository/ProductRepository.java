package com.musinsa.api.repository;

import com.musinsa.api.domain.Product;
import com.musinsa.api.dto.LowestPriceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT new com.musinsa.api.dto.LowestPriceDto(p.category.categoryName, p.brand.brandName, p.price) " +
            "FROM Product p " +
            "WHERE p.price = (SELECT MIN(p2.price) FROM Product p2 WHERE p2.category.categoryId = p.category.categoryId)")
    List<LowestPriceDto> findLowestPriceByCategoryName();
}

package com.musinsa.api.repository;

import com.musinsa.api.domain.Brand;
import com.musinsa.api.domain.Product;
import com.musinsa.api.dto.CategoryLowestPricedItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT new com.musinsa.api.dto.CategoryLowestPricedItemDto(p.category.categoryName, p.brand.brandName, p.price) " +
            "FROM Product p " +
            "WHERE p.price = (SELECT MIN(p2.price) FROM Product p2 WHERE p2.category.categoryId = p.category.categoryId)")
    List<CategoryLowestPricedItemDto> findLowestPriceByCategoryName();

    @Query("SELECT p.brand FROM Product p GROUP BY p.brand HAVING SUM(p.price) = " +
            "(SELECT MIN(subquery.price) FROM (SELECT SUM(p.price) as price FROM Product p GROUP BY p.brand) subquery)")
    List<Brand> findLowestPriceByBrand();

    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.brand = :brand")
    List<Product> findAllByBrand(@Param("brand") Brand brand);
}

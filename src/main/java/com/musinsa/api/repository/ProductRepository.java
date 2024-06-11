package com.musinsa.api.repository;

import com.musinsa.api.domain.Brand;
import com.musinsa.api.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p " +
            "FROM Product p " +
            "JOIN FETCH p.category " +
            "JOIN FETCH p.brand " +
            "WHERE p.price = (SELECT MIN(p2.price) FROM Product p2 WHERE p2.category.categoryId = p.category.categoryId)")
    List<Product> findLowestPriceByCategoryName();

    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.brand = :brand")
    List<Product> findAllByBrand(@Param("brand") Brand brand);
}

package com.musinsa.api.repository;

import com.musinsa.api.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query("SELECT p.brand FROM Product p GROUP BY p.brand HAVING SUM(p.price) = " +
            "(SELECT MIN(subquery.price) FROM (SELECT SUM(p.price) as price FROM Product p GROUP BY p.brand) subquery)")
    List<Brand> findLowestPriceByBrand();
}

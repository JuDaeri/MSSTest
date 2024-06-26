package com.musinsa.api.repository;

import com.musinsa.api.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE c.categoryName = :categoryName")
    Optional<Category> findByCategoryName(@Param("categoryName") String categoryName);
}

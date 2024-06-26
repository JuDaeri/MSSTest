package com.musinsa.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    public Product(Integer price, Category category, Brand brand) {
        this.price = price;
        this.category = category;
        this.brand = brand;
    }

    public void changePrice(Integer price) {
        this.price = price;
    }

    public void changeCategory(Category category) {
        this.category = category;
        this.category.getProducts().add(this);
    }

    public void changeBrand(Brand brand) {
        this.brand = brand;
        this.brand.getProducts().add(this);
    }
}

package com.musinsa.api.service;

import com.musinsa.api.domain.Brand;
import com.musinsa.api.domain.Product;
import com.musinsa.api.dto.BrandAddResp;
import com.musinsa.api.dto.BrandFindResp;
import com.musinsa.api.dto.BrandLowestPricedItemResp;
import com.musinsa.api.dto.BrandUpdateReq;
import com.musinsa.api.repository.BrandRepository;
import com.musinsa.api.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    public BrandAddResp addBrand(String brandName) {
        Brand brand = new Brand(brandName);
        brandRepository.save(brand);

        return new BrandAddResp(brand.getBrandId());
    }

    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

    public void updateBrand(Long id, BrandUpdateReq brandUpdateReq) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("갱신하려는 브랜드가 존재하지 않습니다. brandId : " + id));

        brand.changeBrandName(brandUpdateReq.getBrandName());
    }

    public BrandFindResp.BrandDto findByBrandId(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("찾으려는 브랜드가 존재하지 않습니다. brandId : " + id));
        return new BrandFindResp.BrandDto(brand);
    }

    /**
     * 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회
     */
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
}

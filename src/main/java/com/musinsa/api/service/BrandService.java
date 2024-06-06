package com.musinsa.api.service;

import com.musinsa.api.domain.Brand;
import com.musinsa.api.dto.BrandAddResp;
import com.musinsa.api.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandAddResp addBrand(String brandName) {
        Brand brand = new Brand(brandName);
        brandRepository.save(brand);

        return new BrandAddResp(brand.getBrandId());
    }
}

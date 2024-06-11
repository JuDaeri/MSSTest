package com.musinsa.api.service;

import com.musinsa.api.domain.Brand;
import com.musinsa.api.dto.BrandAddResp;
import com.musinsa.api.dto.BrandFindResp;
import com.musinsa.api.dto.BrandUpdateReq;
import com.musinsa.api.repository.BrandRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

    public void updateBrand(Long id, BrandUpdateReq brandUpdateReq) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("갱신하려는 브랜드가 존재하지 않습니다. id : " + id));

        brand.changeBrandName(brandUpdateReq.getBrandName());
    }

    public BrandFindResp.BrandDto findByBrandId(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("찾으려는 브랜드가 존재하지 않습니다. id : " + id));
        return new BrandFindResp.BrandDto(brand);
    }

}

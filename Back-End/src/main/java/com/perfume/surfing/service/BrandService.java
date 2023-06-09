package com.perfume.surfing.service;


import com.perfume.surfing.domain.Brand;
import com.perfume.surfing.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor    //lombok
public class BrandService {

    private final BrandRepository brandRepository;

    // 브랜드 추가
    // @Transactional(readOnly = true) -> 클래스 전체를 ReadOnly를 취해 DB접근시 속도, 메모리 등 성능을 향상 시키고
    // 추가의 readOnly를 풀어 DB에 값 변경을 할 수 있도록 함
    @Transactional(readOnly = false)
    public int add(Brand brand){
        validateDuplicateBrand(brand);  // 중복 검사
        brandRepository.save(brand);
        return brand.getId();
    }

    private void validateDuplicateBrand(Brand brand) {
        List<Brand> findBrands = brandRepository.findByName(brand.getName());
        if(!findBrands.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 브랜드 입니다.");
        }
    }

    // 검색 =============================
    // 전체 검색
    public List<Brand> findBrands(){
        return brandRepository.findAll();
    }
    // ID로 검색
    public Brand findOne(int brandId){
        return brandRepository.findOne(brandId);
    }
}

package com.example.runi.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.runi.domain.dto.ProductDto;
import com.example.runi.domain.dto.SearchDto;
import com.example.runi.domain.entity.ProductEntity;
import com.example.runi.utils.MemberDetails;
import com.example.runi.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        
    }

    public void save(ProductDto productDto, Integer memberNo) {

        productDto.setMemberNo(memberNo);
        
        ProductEntity proEntity = productDto.toEntity();

        System.out.println("proEntity: " + proEntity);
        productRepository.save(proEntity);

    }

    public List<ProductEntity> getProductSearch(SearchDto request, Integer memberNo) {

        System.out.println(request);

        List<ProductEntity> list;

        if(request.isDtoEntireVariableNull()) {
            list = productRepository.findByMemberNoOrderByNoDesc(memberNo);
        } else {
            list = productRepository.findBySearch(memberNo, request);
        }

        return list;
    }

    @Transactional(readOnly = true)
    public Map<String, String> checkDuplication(ProductDto productDto) {

        Map<String, String> map = new HashMap<>();

        boolean pnDuplicate = productRepository.existsByProductName(productDto.getProductName().trim());
        if (pnDuplicate) {
            map.put("valid_productName","이미 존재하는 상품명입니다.");
        }
        
        return map;
    }
}
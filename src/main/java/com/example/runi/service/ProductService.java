package com.example.runi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.runi.domain.dto.ProductDto;
import com.example.runi.domain.dto.SearchDto;
import com.example.runi.domain.entity.ProductEntity;
import com.example.runi.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        
    }

    public void productSave(ProductDto productDto, Integer memberNo) {

        productDto.setMemberNo(memberNo);
        
        ProductEntity productEntity = productDto.toEntity();

        System.out.println("proEntity: " + productEntity);
        productRepository.save(productEntity);

    }


    //상품내역검색
    public List<ProductEntity> getProductList(SearchDto request, Integer memberNo) {

        System.out.println(request);

        List<ProductEntity> list;

        if(request.isDtoEntireVariableNull()) {
            list = productRepository.findByMemberNoOrderByNoDesc(memberNo);
        } else {
            list = productRepository.findBySearch(request, memberNo);
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

package com.example.runi.member.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import com.example.runi.global.utils.MemberDetails;
import com.example.runi.member.domain.dto.ProductDto;
import com.example.runi.member.domain.dto.SearchDto;
import com.example.runi.member.domain.entity.ProductEntity;
import com.example.runi.member.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        
    }

    public List<ProductEntity> getProduct(SearchDto request, Integer memberNo) {

        System.out.println(request);

        List<ProductEntity> list;

        if(request.isDtoEntireVariableNull()) {
            list = productRepository.findByMemberNoOrderByUpDateDesc(memberNo);
        } else {
            list = productRepository.findBySearch(memberNo, request);
        }

        return list;
    }

    public void save(ProductDto productDto, Integer memberNo) {

        productDto.setMemberNo(memberNo);
        
        ProductEntity proEntity = productDto.toEntity();

        System.out.println("proEntity: " + proEntity);
        productRepository.save(proEntity);

    }
}

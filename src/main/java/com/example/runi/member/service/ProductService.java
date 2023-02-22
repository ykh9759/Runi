package com.example.runi.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.runi.member.domain.dto.ProductDto;
import com.example.runi.member.domain.entity.ProductEntity;
import com.example.runi.member.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void save(ProductDto productDto, Integer memberNo) {

        productDto.setMemberNo(memberNo);
        
        ProductEntity proEntity = productDto.toEntity();

        System.out.println("proEntity: " + proEntity);
        productRepository.save(proEntity);

    }
}

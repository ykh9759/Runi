package com.example.runi.member.repository;

import java.util.List;

import com.example.runi.member.domain.dto.SearchDto;
import com.example.runi.member.domain.entity.ProductEntity;

public interface ProductRepositoryQueryDSL {

    public List<ProductEntity> findBySearch(Integer memberNo, SearchDto request);
    
}

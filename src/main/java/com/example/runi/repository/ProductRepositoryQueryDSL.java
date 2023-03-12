package com.example.runi.repository;

import java.util.List;

import com.example.runi.domain.dto.SearchDto;
import com.example.runi.domain.entity.ProductEntity;

public interface ProductRepositoryQueryDSL {

    public List<ProductEntity> findBySearch(Integer memberNo, SearchDto request);
    
}

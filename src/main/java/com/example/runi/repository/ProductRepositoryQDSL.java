package com.example.runi.repository;

import java.util.List;

import com.example.runi.domain.dto.SearchDto;
import com.example.runi.domain.entity.ProductEntity;

public interface ProductRepositoryQDSL {

    public List<ProductEntity> findBySearch(SearchDto request, Integer memberNo);
    
}

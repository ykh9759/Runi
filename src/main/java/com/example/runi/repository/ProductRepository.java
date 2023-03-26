package com.example.runi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.runi.domain.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>, ProductRepositoryQueryDSL {
    
    public Optional<ProductEntity> findByNo(Integer no);
    public List<ProductEntity> findByMemberNoOrderByNoDesc(Integer memberNo);

    boolean existsByProductName(String productName);
    
}

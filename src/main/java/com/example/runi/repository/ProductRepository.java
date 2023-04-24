package com.example.runi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.runi.domain.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>, ProductRepositoryQDSL {
    
    public Optional<ProductEntity> findByNo(Integer no);
    public List<ProductEntity> findByMemberNoAndSaveStatusOrderByNoDesc(Integer memberNo, String saveStatus);

    boolean existsByProductName(String productName);
    
}

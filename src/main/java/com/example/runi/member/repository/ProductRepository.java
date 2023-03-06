package com.example.runi.member.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.runi.member.domain.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>, ProductRepositoryQueryDSL {
    
    public Optional<ProductEntity> findById(Integer no);
    public List<ProductEntity> findByMemberNoOrderByNoDesc(Integer memberNo);

    boolean existsByProductName(String productName);
    
}

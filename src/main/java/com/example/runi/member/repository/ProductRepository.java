package com.example.runi.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.runi.member.domain.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    
    public Optional<ProductEntity> findById(Integer no);

}

package com.example.runi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.runi.domain.entity.OrderProductEntity;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Integer>{
    
}

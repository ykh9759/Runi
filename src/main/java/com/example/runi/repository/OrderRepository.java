package com.example.runi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.runi.domain.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer>{
    
}

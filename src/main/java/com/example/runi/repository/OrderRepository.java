package com.example.runi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.runi.domain.entity.OrderListEntity;

public interface OrderRepository extends JpaRepository<OrderListEntity, Integer>{
    
}

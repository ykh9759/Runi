package com.example.runi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.runi.domain.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer>{
    
    boolean existsByPhone(String phone);
}

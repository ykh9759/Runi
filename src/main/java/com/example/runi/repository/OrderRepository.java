package com.example.runi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.runi.domain.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer>{

    public Optional<OrderEntity> findByNo(Integer no);
    public List<OrderEntity> findByPhoneOrderByNoDesc(String phone);
    
    boolean existsByPhone(String phone);
    boolean existsByNameAndPhone(String name, String phone);
}

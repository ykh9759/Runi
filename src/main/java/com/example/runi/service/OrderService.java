package com.example.runi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.runi.domain.entity.OrderEntity;
import com.example.runi.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        
    }

    public List<OrderEntity> getOrderList(Integer memberNo) {

        List<OrderEntity> list = orderRepository.findByMemberNoOrderByNoDesc(memberNo);

        return list;
    }
    
}

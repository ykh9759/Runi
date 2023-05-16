package com.example.runi.service;

import org.springframework.stereotype.Service;

import com.example.runi.config.GlobalValue;
import com.example.runi.repository.OrderListRepository;
import com.example.runi.repository.OrderRepository;

@Service
public class DashboardService {

    private final OrderRepository orderRepository;
    private final OrderListRepository orderListRepository;
    private final GlobalValue globalValue;

    public DashboardService(OrderRepository orderRepository, OrderListRepository orderListRepository, GlobalValue globalValue) {
        this.orderRepository = orderRepository;
        this.orderListRepository = orderListRepository;
        this.globalValue = globalValue;
        
    }

    public int getMonthSlaes(Integer memberNo) {

        int monthSales = 0;
        
        monthSales = orderListRepository.getMonthSales(memberNo);

        return monthSales;
    }
    
}

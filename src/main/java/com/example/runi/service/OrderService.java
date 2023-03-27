package com.example.runi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.runi.domain.dto.OrderListDto;
import com.example.runi.domain.entity.OrderEntity;
import com.example.runi.domain.entity.OrderListEntity;
import com.example.runi.repository.OrderListRepository;
import com.example.runi.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderListRepository orderListRepository;

    public OrderService(OrderRepository orderRepository, OrderListRepository orderListRepository) {
        this.orderRepository = orderRepository;
        this.orderListRepository = orderListRepository;
        
    }

    public List<OrderListDto> getOrderList(Integer memberNo) {

        List<OrderListEntity> listeEntities = orderListRepository.findByMemberNoOrderByNoDesc(memberNo);
        List<OrderListDto> listdtos = new ArrayList<>();

        for (OrderListEntity orderListEntity : listeEntities) {
            OrderListDto dto = OrderListDto.builder().entity(orderListEntity).build();
            listdtos.add(dto);
        }


        return listdtos;
    }
    
}

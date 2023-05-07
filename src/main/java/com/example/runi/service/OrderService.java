package com.example.runi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.runi.config.GlobalValue;
import com.example.runi.domain.dto.OrderListDto;
import com.example.runi.domain.dto.OrderStatusDto;
import com.example.runi.domain.dto.SearchDto;
import com.example.runi.domain.entity.OrderEntity;
import com.example.runi.domain.entity.OrderListEntity;
import com.example.runi.repository.OrderListRepository;
import com.example.runi.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderListRepository orderListRepository;
    private final GlobalValue globalValue;

    public OrderService(OrderRepository orderRepository, OrderListRepository orderListRepository, GlobalValue globalValue) {
        this.orderRepository = orderRepository;
        this.orderListRepository = orderListRepository;
        this.globalValue = globalValue;
        
    }

    public List<OrderListDto> getOrderList(SearchDto request, Integer memberNo) {

        List<OrderListEntity> listeEntities = new ArrayList<>();

        if(request.isDtoEntireVariableNull()) {
            listeEntities = orderListRepository.findByMemberNoAndStatusOrderByNoDesc(memberNo, request.getStatus());
        } else {
            listeEntities = orderListRepository.findBySearch(request, memberNo);
        }

        List<OrderListDto> listdtos = new ArrayList<>();

        for (OrderListEntity orderListEntity : listeEntities) {
            OrderListDto dto = OrderListDto.builder().entity(orderListEntity).build();
            listdtos.add(dto);
        }


        //값 매칭
        listdtos.forEach(dto -> dto.setParcel(globalValue.getGlobalValue("parcel", dto.getParcel())));
        listdtos.forEach(dto -> dto.setCashReceipts(globalValue.getGlobalValue("cashReceipts", dto.getCashReceipts())));

        return listdtos;
    }

    public String changeStatus(OrderStatusDto request) {

        Integer no = request.getNo();
        String status = request.getStatus();

        Optional<OrderEntity> order = orderRepository.findByNo(no);
        if(order.isPresent()) {
            order.get().statusUpdate(status);
            orderRepository.save(order.get());
            return "Y";
        } else {
            return "N";
        }
    }
    
}

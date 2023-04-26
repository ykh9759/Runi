package com.example.runi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.runi.config.GlobalValue;
import com.example.runi.domain.dto.OrderListDto;
import com.example.runi.domain.dto.SearchDto;
import com.example.runi.domain.entity.OrderListEntity;
import com.example.runi.repository.OrderListRepository;

@Service
public class OrderService {

    private final OrderListRepository orderListRepository;
    private final GlobalValue globalValue;

    public OrderService(OrderListRepository orderListRepository, GlobalValue globalValue) {
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
    
}

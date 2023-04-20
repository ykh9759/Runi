package com.example.runi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.runi.config.GlobalValue;
import com.example.runi.domain.dto.OrderDto;
import com.example.runi.domain.dto.OrderListDto;
import com.example.runi.domain.entity.MemberEntity;
import com.example.runi.domain.entity.OrderEntity;
import com.example.runi.domain.entity.OrderListEntity;
import com.example.runi.domain.entity.OrderProductEntity;
import com.example.runi.domain.entity.ProductEntity;
import com.example.runi.repository.MemberRepository;
import com.example.runi.repository.OrderListRepository;
import com.example.runi.repository.OrderProductRepository;
import com.example.runi.repository.OrderRepository;
import com.example.runi.repository.ProductRepository;

@Service
public class UserService {
    
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderListRepository orderListRepository;
    private final OrderProductRepository orderProductRepository;
    private final GlobalValue globalValue;

    public UserService(MemberRepository memberRepository, ProductRepository productRepository, OrderRepository orderRepository, OrderListRepository orderListRepository, OrderProductRepository orderProductRepository, GlobalValue globalValue) {
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderListRepository = orderListRepository;
        this.orderProductRepository = orderProductRepository;
        this.globalValue = globalValue;
    }

    public boolean checkId(String id) {

        boolean idDuplicate = memberRepository.existsById(id);
        
        return idDuplicate;
    }

    public boolean checkNameAndPhone(String name, String phone) {

        boolean phoneDuplicate = orderRepository.existsByNameAndPhone(name, phone);
        
        return phoneDuplicate;
    }


    public MemberEntity getMember(String id) {

        Optional<MemberEntity> member = memberRepository.findByIdOrderByNoDesc(id);
        
        return member.get();
    }

    public List<OrderListDto> getOrderList(String phone) {

        List<OrderListEntity> listeEntities = orderListRepository.findByPhoneNoOrderByNoDesc(phone);

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

    public List<ProductEntity> getProductList(Integer memberNo) {

        System.out.println(memberNo);

        List<ProductEntity> list = productRepository.findByMemberNoOrderByNoDesc(memberNo);

        return list;
    }


    //상품주문
    public void orderSave(OrderDto dto) {

        //주문내역저장
        OrderEntity orderEntity =  OrderEntity.builder()
                                                .dto(dto)
                                                .build();

        orderRepository.save(orderEntity);

        //주문상품내역 저장
        for(int i = 0; i < dto.getProductNo().size(); i++) 
        {
            int pCnt = dto.getProductCnt().get(i);
            int pPrice = 0;

            //해당상품 가져온다.
            Optional<ProductEntity> productEntity = productRepository.findByNo(dto.getProductNo().get(i));

            //상품가격과 수량을 곱한 값
            pPrice = productEntity.get().getPrice() * pCnt;

            OrderProductEntity orderProductEntity = OrderProductEntity.builder()
                                                                        .oNo(orderEntity)
                                                                        .pNo(productEntity.get())
                                                                        .pCnt(pCnt)
                                                                        .pPrice(pPrice)
                                                                        .build();

            orderProductRepository.save(orderProductEntity);
        }
        
    }

    @Transactional(readOnly = true)
    public Map<String, String> checkDuplication(OrderDto OrderDto) {

        Map<String, String> map = new HashMap<>();

        boolean Duplicate = orderRepository.existsByPhone(OrderDto.getPhone().trim());
        if (Duplicate) {
            map.put("valid_phone","해당 휴대폰번호는 이미 주문하셨습니다.");
        }
        
        return map;
    }


}

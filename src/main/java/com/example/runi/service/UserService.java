package com.example.runi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.runi.domain.dto.OrderDto;
import com.example.runi.domain.entity.MemberEntity;
import com.example.runi.domain.entity.OrderListEntity;
import com.example.runi.domain.entity.ProductEntity;
import com.example.runi.repository.MemberRepository;
import com.example.runi.repository.OrderRepository;
import com.example.runi.repository.ProductRepository;

@Service
public class UserService {
    
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public UserService(MemberRepository memberRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }


    public boolean checkId(String id) {

        boolean idDuplicate = memberRepository.existsById(id);
        
        return idDuplicate;
    }


    public MemberEntity getMember(String id) {

        Optional<MemberEntity> member = memberRepository.findByIdOrderByNoDesc(id);
        
        return member.get();
    }

    public List<ProductEntity> getProductMember(Integer memberNo) {

        System.out.println(memberNo);

        List<ProductEntity> list = productRepository.findByMemberNoOrderByNoDesc(memberNo);

        return list;
    }

    public void orderListSave(OrderDto dto) {

        for(int i = 0; i < dto.getProductNo().size(); i++) 
        {
            OrderListEntity orderListEntity =  OrderListEntity.builder()
                                                .dto(dto)
                                                .pNo(dto.getProductNo().get(i))
                                                .pCnt(dto.getProductCnt().get(i))
                                                .build();

            orderRepository.save(orderListEntity);
        }
        
    }


}

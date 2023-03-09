package com.example.runi.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.runi.member.domain.entity.MemberEntity;
import com.example.runi.member.domain.entity.ProductEntity;
import com.example.runi.member.repository.MemberRepository;
import com.example.runi.member.repository.ProductRepository;

@Service
public class UserService {
    
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public UserService(MemberRepository memberRepository, ProductRepository productRepository) {
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
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


}

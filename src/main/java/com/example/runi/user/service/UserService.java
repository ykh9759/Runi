package com.example.runi.user.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.runi.member.repository.MemberRepository;

@Service
public class UserService {
    
    private final MemberRepository memberRepository;

    public UserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public boolean checkId(String id) {

        boolean idDuplicate = memberRepository.existsById(id);
        
        return idDuplicate;
    }


}

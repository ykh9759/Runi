package com.example.runi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.runi.domain.entity.LoginHistoryEntity;
import com.example.runi.domain.entity.MemberEntity;
import com.example.runi.repository.LoginHistoryRepository;
import com.example.runi.repository.MemberRepository;

@Service
public class MypageService {

    private final MemberRepository memberRepository;
    private final LoginHistoryRepository loginHistoryRepository;

    public MypageService(MemberRepository memberRepository, LoginHistoryRepository loginHistoryRepository) {
        this.memberRepository = memberRepository;
        this.loginHistoryRepository = loginHistoryRepository;
    }

    public List<LoginHistoryEntity> getLoginHistory(Integer memberNo) {

        List<LoginHistoryEntity> history = loginHistoryRepository.findByMemberNo(memberNo);

        return history;

    }

    public MemberEntity getMember(Integer memberNo) {

        Optional<MemberEntity> member = memberRepository.findByNo(memberNo);

        return member.get();

    }
    
}

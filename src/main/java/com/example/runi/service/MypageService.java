package com.example.runi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.runi.domain.dto.ProfileDto;
import com.example.runi.domain.entity.LoginHistoryEntity;
import com.example.runi.domain.entity.MemberEntity;
import com.example.runi.repository.LoginHistoryRepository;
import com.example.runi.repository.MemberRepository;

@Service
public class MypageService {

    private final MemberRepository memberRepository;
    private final LoginHistoryRepository loginHistoryRepository;
    private final PasswordEncoder passwordEncoder;

    public MypageService(MemberRepository memberRepository, LoginHistoryRepository loginHistoryRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.loginHistoryRepository = loginHistoryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<LoginHistoryEntity> getLoginHistory(Integer memberNo) {

        List<LoginHistoryEntity> history = loginHistoryRepository.findByMemberNo(memberNo);

        return history;

    }

    public MemberEntity getMember(Integer memberNo) {

        Optional<MemberEntity> member = memberRepository.findByNo(memberNo);

        return member.get();

    }

    public void updateMember(Integer memberNo, ProfileDto reqeust) {

        Optional<MemberEntity> member = memberRepository.findByNo(memberNo);
        if(member.isPresent()) {
            String password = passwordEncoder.encode(reqeust.getPassword());
            member.get().encryptPassword(password);
            memberRepository.save(member.get()); 
        }
    }
    
}

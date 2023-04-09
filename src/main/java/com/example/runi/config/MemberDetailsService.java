package com.example.runi.config;

import java.util.Optional;

import com.example.runi.domain.entity.MemberEntity;
import com.example.runi.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        Optional<MemberEntity> member = memberRepository.findById(id);

        member.orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 사용자 입니다"));
        
        return new MemberDetails(member.get());
    }
}
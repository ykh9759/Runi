package com.example.runi.service;

import com.example.runi.config.MemberDetails;
import com.example.runi.domain.dto.LoginDto;
import com.example.runi.domain.dto.SignupDto;
import com.example.runi.domain.entity.LoginHistoryEntity;
import com.example.runi.domain.entity.MemberEntity;
import com.example.runi.repository.LoginHistoryRepository;
import com.example.runi.repository.MemberRepository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final LoginHistoryRepository loginHistoryRepository;

    public AuthService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, LoginHistoryRepository loginHistoryRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.loginHistoryRepository = loginHistoryRepository;
    }

    // public MemberDetails login(LoginDto request) throws Exception {
    //     Authentication authentication = authenticationManager.authenticate(
    //             new UsernamePasswordAuthenticationToken(request.getId(), request.getPassword()));

    //     SecurityContextHolder.getContext().setAuthentication(authentication);
        
    //     MemberDetails principal = (MemberDetails) authentication.getPrincipal();

    //     System.out.println(principal);
        
    //     return principal;
    // }

    @Transactional
    public String signup(SignupDto request) {
        boolean existMember = memberRepository.existsById(request.getId());

        // 이미 회원이 존재하는 경우
        if (existMember) return null;

        MemberEntity member = new MemberEntity(request);
        member.encryptPassword(passwordEncoder);

        memberRepository.save(member);
        return member.getId();
    }


    @Transactional(readOnly = true)
    public Map<String, String> checkDuplication(SignupDto dto) {

        Map<String, String> map = new HashMap<>();

        boolean idDuplicate = memberRepository.existsById(dto.getId());
        if (idDuplicate) {
            map.put("valid_id","이미 존재하는 아이디입니다.");
        }

        boolean emailuplicate = memberRepository.existsByEmail(dto.getEmail());
        if (emailuplicate) {
            map.put("valid_email","이미 존재하는 이메일입니다.");
        }
        
        return map;
    }

}

package com.example.runi.member.service;

import com.example.runi.member.repository.MemberRepository;
import com.example.runi.member.domain.entity.MemberEntity;
import com.example.runi.global.utils.MemberDetails;
import com.example.runi.member.domain.dto.LoginDto;
import com.example.runi.member.domain.dto.SignupDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public String login(LoginDto request) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getId(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        MemberDetails principal = (MemberDetails) authentication.getPrincipal();

        System.out.println(principal);
        
        return principal.getUsername();
    }

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

package com.example.runi.member.service;

import com.example.runi.member.repository.MemberRepository;
import com.example.runi.utils.MemberDetails;
import com.example.runi.member.domain.entity.MemberEntity;
import com.example.runi.member.domain.dto.LoginDto;
import com.example.runi.member.domain.dto.SignupDto;

import java.util.HashMap;
import java.util.Map;

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
@Transactional
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

    public String signup(SignupDto request) {
        boolean existMember = memberRepository.existsById(request.getId());

        // 이미 회원이 존재하는 경우
        if (existMember) return null;

        MemberEntity member = new MemberEntity(request);
        member.encryptPassword(passwordEncoder);

        memberRepository.save(member);
        return member.getId();
    }

    /* 회원가입 시, 유효성 체크 */
    @Transactional(readOnly = true)
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
    
        /* 유효성 검사에 실패한 필드 목록을 받음 */
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    @Transactional(readOnly = true)
    public void checkUsernameDuplication(SignupDto dto) {
        boolean idDuplicate = memberRepository.existsById(dto.getId());
        if (idDuplicate) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }

        boolean emailuplicate = memberRepository.existsById(dto.getEmail());
        if (emailuplicate) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }
}

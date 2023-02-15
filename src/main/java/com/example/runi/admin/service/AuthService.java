package com.example.runi.admin.service;

import com.example.runi.admin.repository.AdminRepository;
import com.example.runi.admin.domain.entity.AdminEntity;
import com.example.runi.admin.domain.dto.SignupDto;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public String signup(SignupDto request) {
        boolean existMember = adminRepository.existsById(request.getId());

        // 이미 회원이 존재하는 경우
        if (existMember) return null;

        AdminEntity admin = new AdminEntity(request);
        admin.encryptPassword(passwordEncoder);

        adminRepository.save(admin);
        return admin.getId();
    }
}

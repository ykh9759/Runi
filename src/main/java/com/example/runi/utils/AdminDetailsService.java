package com.example.runi.utils;

import java.util.Optional;

import com.example.runi.admin.domain.entity.AdminEntity;
import com.example.runi.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        Optional<AdminEntity> admin = adminRepository.findById(id);

        System.out.print(admin.get());

        admin.orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 사용자 입니다"));
        
        return new AdminDetails(admin.get());
    }
}
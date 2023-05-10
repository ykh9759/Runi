package com.example.runi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.runi.domain.entity.LoginHistoryEntity;
import com.example.runi.repository.LoginHistoryRepository;

@Service
public class MypageService {

    private final LoginHistoryRepository loginHistoryRepository;

    public MypageService(LoginHistoryRepository loginHistoryRepository) {
        this.loginHistoryRepository = loginHistoryRepository;
    }

    public List<LoginHistoryEntity> getLoginHistory(Integer memberNo) {

        List<LoginHistoryEntity> history = loginHistoryRepository.findByMemberNo(memberNo);

        return history;

    }
    
}

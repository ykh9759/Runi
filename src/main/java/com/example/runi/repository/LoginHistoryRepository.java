package com.example.runi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.runi.domain.entity.LoginHistoryEntity;

public interface LoginHistoryRepository  extends JpaRepository<LoginHistoryEntity, Integer> {

    
}

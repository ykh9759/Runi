package com.example.runi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.runi.domain.entity.LoginHistoryEntity;

public interface LoginHistoryRepository  extends JpaRepository<LoginHistoryEntity, Integer> {

    
}

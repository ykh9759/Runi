package com.example.runi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.runi.domain.entity.GlobalValueEntity;

public interface GlobalValueRepository  extends JpaRepository<GlobalValueEntity, Integer> {

    public List<GlobalValueEntity> findAll();

    public Optional<GlobalValueEntity> findByCategoryAndCode(String category, String code);
    
}

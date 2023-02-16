package com.example.runi.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.runi.admin.domain.entity.AdminEntity;

public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {

    public Optional<AdminEntity> findById(String id);


    boolean existsById(String id);
    boolean existsByEmail(String email);

}

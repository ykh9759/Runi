package com.example.runi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.runi.domain.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

    public Optional<MemberEntity> findById(String id);
    public Optional<MemberEntity> findByIdOrderByNoDesc(String id);


    boolean existsById(String id);
    boolean existsByEmail(String email);

}

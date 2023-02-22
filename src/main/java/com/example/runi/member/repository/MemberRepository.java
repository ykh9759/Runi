package com.example.runi.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.runi.member.domain.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

    public Optional<MemberEntity> findById(String id);


    boolean existsById(String id);
    boolean existsByEmail(String email);

}

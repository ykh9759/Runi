package com.example.runi.member.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.runi.member.domain.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>, ProductRepositoryCustom {
    
    public Optional<ProductEntity> findById(Integer no);
    public List<ProductEntity> findByMemberNoOrderByUpDateDesc(Integer memberNo);
    public List<ProductEntity> findByMemberNoAndSaveDateGreaterThanEqualOrderByUpDateDesc(Integer userNo,
            LocalDate startDate);
    public List<ProductEntity> findByMemberNoAndSaveDateLessThanEqualOrderByUpDateDesc(Integer memberNo,
            LocalDate endDate);
    public List<ProductEntity> findByMemberNoAndSaveDateBetweenOrderByUpDateDesc(Integer memberNo, LocalDate startDate,
            LocalDate endDate);
}

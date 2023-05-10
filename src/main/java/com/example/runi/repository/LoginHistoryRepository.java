package com.example.runi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.runi.domain.entity.LoginHistoryEntity;
import java.util.List;


public interface LoginHistoryRepository  extends JpaRepository<LoginHistoryEntity, Integer> {

    List<LoginHistoryEntity> findByMemberNo(Integer memberNo);
    
}

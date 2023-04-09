package com.example.runi.config;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.runi.domain.entity.GlobalValueEntity;
import com.example.runi.repository.GlobalValueRepository;

@Component
public class GlobalValue {

    final GlobalValueRepository globalValueRepository;

    public GlobalValue(GlobalValueRepository globalValueRepository) {
        this.globalValueRepository = globalValueRepository;
    }
    
    public String getGlobalValue(String category, String code) {

        Optional<GlobalValueEntity> entity = globalValueRepository.findByCategoryAndCode(category, code);

        if(entity.isPresent()) {
            return entity.get().getValue();
        } else {
            return "";
        }
        
    }
}

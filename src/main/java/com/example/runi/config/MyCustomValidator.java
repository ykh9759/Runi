package com.example.runi.config;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.runi.domain.dto.SearchDto;

import lombok.Data;

@Data
public class MyCustomValidator implements ConstraintValidator<MyCustomValidation, SearchDto> {
    
    @Override
    public boolean isValid(SearchDto searchDto, ConstraintValidatorContext context) {

        System.out.println(searchDto.getSelect());
        System.out.println(searchDto.getSearch());
        
        // startDate와 endDate를 이용하여 검사 로직을 구현
        
        return true; // 또는 false, 유효성 검사 결과를 반환
    }

}

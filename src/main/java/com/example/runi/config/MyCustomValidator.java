package com.example.runi.config;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.runi.domain.dto.SearchDto;

public class MyCustomValidator implements ConstraintValidator<MyCustomValidation, SearchDto> {
    @Override
    public boolean isValid(SearchDto value, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation(); // 기본 오류 메시지를 비활성화
        context.buildConstraintViolationWithTemplate("Custom validation failed: Value is null or empty")
                    .addConstraintViolation(); // 커스텀 오류 메시지를 추가

        return false;
        // 유효성 검사 로직을 구현
        // value는 검사할 필드의 값, context는 유효성 검사 컨텍스트
        // 유효성 검사 결과에 따라 true 또는 false를 반환
        // 오류 발생시 context.buildConstraintViolationWithTemplate() 등을 사용하여 오류 메시지를 추가할 수 있음
    }
}

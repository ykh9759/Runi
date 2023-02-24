package com.example.runi.global.utils;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

public class Func {
    
    //유효성 체크
    public static Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
    
        /* 유효성 검사에 실패한 필드 목록을 받음 */
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

}

package com.example.runi.domain.dto;

import java.lang.reflect.Field;

import javax.validation.constraints.Pattern;

import com.example.runi.config.MyCustomValidation;

import lombok.Data;

@Data
public class SearchDto {
 
    @Pattern(regexp = "^(|[0-9]{4}-[0-9]{2}-[0-9]{2})$", message = "날짜는 YYYY-MM-DD 형태로 입력해주세요.")
    private String startDate;

    @Pattern(regexp = "^(|[0-9]{4}-[0-9]{2}-[0-9]{2})$", message = "날짜는 YYYY-MM-DD 형태로 입력해주세요.")
    private String endDate;
    
    @MyCustomValidation
    private String select;

    @MyCustomValidation
    private String search;

    public boolean isDtoEntireVariableNull() {
        try {
            for (Field f : getClass().getDeclaredFields()) {

                if(f.getName().equals("select")) continue;

                if (f.get(this) != null && !f.get(this).equals("")) {
                    return false;
                }
            }
            return true;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

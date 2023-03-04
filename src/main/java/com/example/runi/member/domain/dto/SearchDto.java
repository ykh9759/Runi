package com.example.runi.member.domain.dto;

import java.lang.reflect.Field;

import lombok.Data;

@Data
public class SearchDto {
 
    private String startDate;
    private String endDate;
    private String select;
    private String search;

    public boolean isDtoEntireVariableNull() {
        try {
            for (Field f : getClass().getDeclaredFields()) {
                if (f.get(this) != null) {
                    return false;
                }
            }
            return true;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

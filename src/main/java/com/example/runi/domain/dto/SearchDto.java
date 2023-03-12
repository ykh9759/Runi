package com.example.runi.domain.dto;

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

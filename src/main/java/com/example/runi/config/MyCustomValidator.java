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

        if(searchDto.getSelect() != null && searchDto.getSearch() != null) {

            if(searchDto.getSelect().equals("0")) {

                try {
                    if(!searchDto.getSearch().isEmpty()) {
                        int search = Integer.parseInt(searchDto.getSearch());
                    }
                } catch(Exception e) {
                    return false;
                }
            }
        }

        return true;
    }

}

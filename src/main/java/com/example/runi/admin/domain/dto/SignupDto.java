package com.example.runi.admin.domain.dto;

import javax.validation.constraints.Email;

import lombok.Data;

@Data
public class SignupDto {
    
    private String name;
    private String id;
    private String password;
    private String repeatPassword;

    @Email
    private String email;
}

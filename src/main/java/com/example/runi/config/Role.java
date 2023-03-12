package com.example.runi.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    MEMBER("MEMBER"),
    ADMIN("ADMIN");

    private String value;
}

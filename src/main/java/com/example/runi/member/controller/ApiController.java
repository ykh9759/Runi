package com.example.runi.member.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/member/api")
public class ApiController {

    @RequestMapping("/create-code") 
    public String createCode() {
        return "hello";

    }
    
}

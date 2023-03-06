package com.example.runi.member.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.runi.global.utils.MemberDetails;
import com.example.runi.member.domain.dto.SearchDto;
import com.example.runi.member.domain.entity.ProductEntity;
import com.example.runi.member.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/member/api")
public class ApiController {

    private final ProductService productservice;

    public ApiController(ProductService productservice) {
        this.productservice = productservice;
    }

    @RequestMapping("/create-code") 
    public String createCode() {
        return "hello";

    }

}

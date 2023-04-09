package com.example.runi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.runi.service.ProductService;
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

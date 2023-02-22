package com.example.runi.member.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.runi.member.domain.dto.ProductDto;
import com.example.runi.member.service.ProductService;
import com.example.runi.utils.MemberDetails;

@Controller
@RequestMapping("/member")
public class ProductController {
    
    private final ProductService productservice;

    public ProductController(ProductService productservice) {
        this.productservice = productservice;
    }

    @GetMapping("/product-list")
    public String listView() {
        return "member/product/list";
    }

    @GetMapping("/product-create")
    public String createView() {
        return "member/product/create";
    }

    @PostMapping("/product-create")
    public String productCreate(ProductDto productDto, @AuthenticationPrincipal MemberDetails memberDetails) {
        
        Integer memberNo = memberDetails.getUserNo();

        System.out.println("MEMBER_NO: " + memberNo);
        System.out.println("productDto: " + productDto);
        productservice.save(productDto, memberNo);


        return "member/product/create";
    }
}

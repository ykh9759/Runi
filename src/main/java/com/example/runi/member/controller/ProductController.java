package com.example.runi.member.controller;

import com.example.runi.global.utils.Func;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.Errors;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

import com.example.runi.global.utils.MemberDetails;
import com.example.runi.member.domain.dto.ProductDto;
import com.example.runi.member.domain.entity.ProductEntity;
import com.example.runi.member.service.ProductService;

import javax.validation.Valid;


@Controller
@RequestMapping("/member")
public class ProductController {
    
    private final ProductService productservice;

    public ProductController(ProductService productservice) {
        this.productservice = productservice;
    }

    @GetMapping("/product-list")
    public String listView(ProductDto request, Model model,  @AuthenticationPrincipal MemberDetails memberDetails) {

        List<ProductEntity> products = productservice.getProduct(memberDetails.getUserNo());

        model.addAttribute("products", products);
        model.addAttribute("ProductDto", request);

        return "member/product/list";
    }

    @PostMapping("/product-create")
    public String productCreate(@Valid ProductDto request, Errors errors, Model model, @AuthenticationPrincipal MemberDetails memberDetails) {
        
        Integer memberNo = memberDetails.getUserNo();
        List<ProductEntity> products = productservice.getProduct(memberDetails.getUserNo());

        model.addAttribute("products", products);

        //회원가입 실패시 입력 데이터 값을 유지
        model.addAttribute("ProductDto", request);

        //유효성체크
        if (errors.hasErrors()) {

            Map<String, String> validatorResult = Func.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "member/product/list";
        }

        System.out.println("MEMBER_NO: " + memberNo);
        System.out.println("productDto: " + request);
        productservice.save(request, memberNo);

        List<ProductEntity> products2 = productservice.getProduct(memberDetails.getUserNo());

        model.addAttribute("products", products2);

        return "member/product/list";
    }
}

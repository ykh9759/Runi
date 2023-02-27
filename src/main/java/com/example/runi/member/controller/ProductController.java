package com.example.runi.member.controller;

import com.example.runi.global.utils.Func;

import org.json.simple.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.validation.Errors;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

import com.example.runi.global.utils.MemberDetails;
import com.example.runi.member.domain.dto.ProductDto;
import com.example.runi.member.domain.entity.ProductEntity;
import com.example.runi.member.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.Valid;


@Controller
@RequestMapping("/member")
public class ProductController {
    
    private final ProductService productservice;

    public ProductController(ProductService productservice) {
        this.productservice = productservice;
    }

    //상품내역
    @GetMapping("/product-list")
    public String listView(ProductDto request, Model model,  @AuthenticationPrincipal MemberDetails memberDetails) {

        List<ProductEntity> products = productservice.getProduct(memberDetails.getUserNo());

        model.addAttribute("products", products);

        return "member/product/list";
    }


    //상품등록
    @PostMapping("/product-create")
    @ResponseBody
    public String productCreate(@Valid ProductDto request, Errors errors, @AuthenticationPrincipal MemberDetails memberDetails) {
        
        Integer memberNo = memberDetails.getUserNo();
        ObjectMapper obj = new ObjectMapper();
        JSONObject result;

        Map<String, String> requestMap = obj.convertValue(request, Map.class);

        //유효성체크
        if (errors.hasErrors()) {

            Map<String, String> validatorResult = Func.validateHandling(errors);

            validatorResult.forEach((key, value) -> requestMap.merge(key, value, (v1, v2) -> v2));

            requestMap.put("msg", "N");            
            result = new JSONObject(requestMap);
            System.out.println(result);
            return result.toJSONString();
        }

        System.out.println("MEMBER_NO: " + memberNo);
        System.out.println("productDto: " + request);
        productservice.save(request, memberNo);

        requestMap.put("msg", "Y");
        result = new JSONObject(requestMap);

        return result.toJSONString();
    }
}

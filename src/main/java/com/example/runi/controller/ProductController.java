package com.example.runi.controller;

import com.example.runi.domain.dto.ProductDto;
import com.example.runi.domain.dto.SearchDto;
import com.example.runi.domain.entity.ProductEntity;
import com.example.runi.utils.Func;

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
import java.util.HashMap;

import com.example.runi.utils.MemberDetails;
import com.example.runi.service.ProductService;

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

        return "member/product/list";
    }


    //상품등록
    @PostMapping("/product-create")
    @ResponseBody
    public String productCreate(@Valid ProductDto request, Errors errors, @AuthenticationPrincipal MemberDetails memberDetails) {
        
        Integer memberNo = memberDetails.getUserNo();
        Map<String, String> resultMap = new HashMap<String,String>();
        JSONObject result;

        //유효성체크
        if (errors.hasErrors()) {

            resultMap = Func.validateHandling(errors);

            resultMap.put("msg", "N");            
            result = new JSONObject(resultMap);
            System.out.println(result);
            return result.toJSONString();
        }

        //중복체크
        Map<String, String> dupResult = productservice.checkDuplication(request);
        if(!dupResult.isEmpty()) {

            resultMap.put("msg", "N"); 

            for (String key : dupResult.keySet()) {
                resultMap.put(key, dupResult.get(key)); 
            }

            result = new JSONObject(resultMap);
            return result.toJSONString();
        }

        System.out.println("MEMBER_NO: " + memberNo);
        System.out.println("productDto: " + request);
        productservice.productSave(request, memberNo);


        resultMap.put("msg", "Y");
        result = new JSONObject(resultMap);

        return result.toJSONString();
    }

    @PostMapping("/getProductList")
    @ResponseBody
    public List<ProductEntity> getProductList(SearchDto request, @AuthenticationPrincipal MemberDetails memberDetails) {
        
        System.out.println(request);

        List<ProductEntity> products = productservice.getProductList(request, memberDetails.getUserNo());


        // System.out.println(products.toString());
        return products;
    }
}

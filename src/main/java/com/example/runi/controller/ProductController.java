package com.example.runi.controller;

import com.example.runi.config.MemberDetails;
import com.example.runi.domain.dto.ProductDto;
import com.example.runi.domain.dto.SearchDto;
import com.example.runi.domain.entity.ProductEntity;
import com.example.runi.utils.Func;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.validation.Errors;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
        Map<String, String> dupResult = productservice.checkDuplication(request, "INSERT");
        if(!dupResult.isEmpty()) {

            resultMap.put("msg", "N"); 

            for (String key : dupResult.keySet()) {
                resultMap.put(key, dupResult.get(key)); 
            }

            result = new JSONObject(resultMap);
            return result.toJSONString();
        }

        request.setSaveStatus("Y");

        System.out.println("MEMBER_NO: " + memberNo);
        System.out.println("productDto: " + request);
        productservice.productSave(request, memberNo);


        resultMap.put("msg", "Y");
        result = new JSONObject(resultMap);

        return result.toJSONString();
    }

    //상품수정
    @PostMapping("/product-update")
    @ResponseBody
    public String productUpdate(@Valid ProductDto request, Errors errors) {
        
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
        Map<String, String> dupResult = productservice.checkDuplication(request , "UPDATE");
        if(!dupResult.isEmpty()) {

            resultMap.put("msg", "N"); 

            for (String key : dupResult.keySet()) {
                resultMap.put(key, dupResult.get(key)); 
            }

            result = new JSONObject(resultMap);
            return result.toJSONString();
        }

        System.out.println("productDto: " + request);
        productservice.productUpdate(request);


        resultMap.put("msg", "Y");
        result = new JSONObject(resultMap);

        return result.toJSONString();
    }

    //주문취소
    @RequestMapping("/product-delete")
    @ResponseBody
    public ResponseEntity<?> orderCancel(@RequestParam("no") Integer no) {
        System.out.println(no);
        String result = "";

        result = productservice.productDelete(no);

        System.out.println(result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/getProductList")
    @ResponseBody
    public ResponseEntity<?> getProductList(@Valid SearchDto request, Errors errors, @AuthenticationPrincipal MemberDetails memberDetails) {
        
        System.out.println(request);

        //유효성체크
        if (errors.hasErrors()) {

            Map<String, String> validatorResult = new HashMap<String, String>();
            validatorResult = Func.validateHandling(errors);
            
            return new ResponseEntity<>(validatorResult, HttpStatus.BAD_REQUEST);
        }

        List<ProductEntity> products = productservice.getProductList(request, memberDetails.getUserNo());

        System.out.println(products);

        return new ResponseEntity<>(products, HttpStatus.OK);

    }
}

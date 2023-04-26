package com.example.runi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.runi.config.MemberDetails;
import com.example.runi.domain.dto.OrderListDto;
import com.example.runi.domain.dto.SearchDto;
import com.example.runi.service.OrderService;
import com.example.runi.utils.Func;


@Controller
@RequestMapping("/member")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //주문내역
    @GetMapping("/order-list")
    public String orderList() {

        return "member/order/list";
    }

    //주문코드생성
    @GetMapping("/create-code")
    public String createCode() {

        return "member/order/create_code";
    }

    @PostMapping("/getOrderList")
    @ResponseBody
    public ResponseEntity<?> getProductList(@Valid SearchDto request, Errors errors, @AuthenticationPrincipal MemberDetails memberDetails) {
        
        //유효성체크
        if (errors.hasErrors()) {

            Map<String, String> validatorResult = new HashMap<String, String>();
            validatorResult = Func.validateHandling(errors);
            
            return new ResponseEntity<>(validatorResult, HttpStatus.BAD_REQUEST);
        }

        if(request.getStatus() == null) request.setStatus("A");

        System.out.println(request);
        List<OrderListDto> orders = orderService.getOrderList(request, memberDetails.getUserNo());


        // System.out.println(products.toString());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}

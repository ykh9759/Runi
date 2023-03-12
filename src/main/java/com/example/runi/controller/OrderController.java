package com.example.runi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/member")
public class OrderController {

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
}

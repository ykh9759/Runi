package com.example.runi.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.runi.domain.dto.OrderListDto;
import com.example.runi.domain.dto.SearchDto;
import com.example.runi.domain.entity.OrderEntity;
import com.example.runi.domain.entity.OrderListEntity;
import com.example.runi.service.OrderService;
import com.example.runi.utils.MemberDetails;


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
    public List<OrderListDto> getProductList(SearchDto request, @AuthenticationPrincipal MemberDetails memberDetails) {
        

        List<OrderListDto> orders = orderService.getOrderList(request, memberDetails.getUserNo());


        // System.out.println(products.toString());
        return orders;
    }
}

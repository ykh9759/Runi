package com.example.runi.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("")
    public String index() {

        return "redirect:/admin/login";
    }

    @GetMapping("/login")
    public String login() {

        return "admin/page/login";
    }

    @GetMapping("/signup")
    public String signUp() {

        return "admin/page/signup";
    }

    @GetMapping("/order-list")
    public String orderList() {

        return "admin/page/order_list";
    }

}

package com.example.runi.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.runi.admin.domain.dto.SignupDto;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("")
    public String index() {

        return "admin/page/dashboard";
    }

    @GetMapping("/login")
    public String login() {

        return "admin/page/login";
    }

    @GetMapping("/loginFail")
    public String loginFail(@RequestParam(value = "exception", required = false)String exception, Model model) {

        model.addAttribute("exception", exception);
        
        return "admin/page/login";
    }

    @GetMapping("/signup")
    public String signUp(Model model, SignupDto reqeust) {

        //최초 빈값 세팅
        model.addAttribute("signupDto", reqeust);

        return "admin/page/signup";
    }

    @GetMapping("/order-list")
    public String orderList() {

        return "admin/page/order_list";
    }

    @GetMapping("/create-code")
    public String createCode() {

        return "admin/page/create_code";
    }
}

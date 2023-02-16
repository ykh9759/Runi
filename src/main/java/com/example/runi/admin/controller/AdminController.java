package com.example.runi.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.runi.admin.domain.dto.SignupDto;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("")
    public String index() {

        return "redirect:/admin/login";
    }

    @GetMapping("/login")
    public String login() {

        return "/admin/page/login";
    }

    @GetMapping("/signup")
    public String signUp(Model model, SignupDto reqeust) {

        //최초 빈값 세팅
        model.addAttribute("signupDto", reqeust);

        return "/admin/page/signup";
    }

    @GetMapping("/order-list")
    public String orderList() {

        return "/admin/page/order_list";
    }

    @GetMapping("/dashboard")
    public String dashboard() {

        return "/admin/page/dashboard";
    }

}

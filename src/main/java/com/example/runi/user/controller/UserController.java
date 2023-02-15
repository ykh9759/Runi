package com.example.runi.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("")
    public String index() {

        return "index";
    }

    @PostMapping("/password-check")
    public String passwordCheck(@RequestParam String password, Model model) {

        model.addAttribute("password", password);
        return "redirect:/user/order";
    }

    @GetMapping("/order")
    public String order() {

        return "user/page/order";
    }

}

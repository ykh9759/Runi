package com.example.runi.Controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.tags.Param;

@Controller
@RequestMapping("/user")
public class UserController {

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

package com.example.runi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.runi.domain.entity.MemberEntity;
import com.example.runi.domain.entity.ProductEntity;
import com.example.runi.service.ProductService;
import com.example.runi.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String index(Model model) {

        return "user/index";
    }

    @PostMapping("/order")
    public String order(@RequestParam String id, Model model, RedirectAttributes redirectAttributes) {

        //아이디 체크
        boolean dupResult = userService.checkId(id);
        if(dupResult) {

            MemberEntity member = userService.getMember(id);

            List<ProductEntity> products = userService.getProductMember(member.getNo());
            
            model.addAttribute("id", id);
            model.addAttribute("products", products);
            return "user/order";
        } else {
            redirectAttributes.addFlashAttribute("error","존재하지 않는 아이디입니다.");
            return "redirect:/user";
        }
    }

}

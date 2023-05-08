package com.example.runi.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.runi.config.MemberDetails;

@Controller
@RequestMapping("/member")
public class MypageController {

    @GetMapping("login-history")
    public String index(@AuthenticationPrincipal MemberDetails memberDetails) {

        


        return "member/mypage/login-history";
    }
    
}

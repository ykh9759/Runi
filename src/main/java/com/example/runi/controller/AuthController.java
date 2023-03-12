package com.example.runi.controller;

import com.example.runi.domain.dto.LoginDto;
import com.example.runi.domain.dto.SignupDto;
import com.example.runi.utils.Func;
import com.example.runi.service.AuthService;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //로그인
    @GetMapping("/login")
    public String login() {

        return "member/auth/login";
    }

    //로그인실패
    @GetMapping("/loginFail")
    public String loginFail(@RequestParam(value = "exception", required = false)String exception, Model model) {

        model.addAttribute("exception", exception);
        
        return "member/auth/login";
    }

    //회원가입
    @GetMapping("/signup")
    public String signUp(Model model, SignupDto request) {

        //최초 빈값 세팅
        model.addAttribute("signupDto", request);

        return "member/auth/signup";
    }

    @PostMapping("/login")
    public String login(@Valid LoginDto request) {
        try {
            return authService.login(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping("/signup")
    public String signup(@Valid SignupDto request, Errors errors, Model model) {

        //회원가입 실패시 입력 데이터 값을 유지
        model.addAttribute("signupDto", request);

        //유효성체크
        if (errors.hasErrors()) {

            Map<String, String> validatorResult = Func.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "member/auth/signup";
        }


        //중복체크
        Map<String, String> dupResult = authService.checkDuplication(request);
        if(!dupResult.isEmpty()) {

            for (String key : dupResult.keySet()) {
                model.addAttribute(key, dupResult.get(key));
            }

             return "member/auth/signup";
        }

        authService.signup(request);
        
        return "redirect:/member/login";
    }
}

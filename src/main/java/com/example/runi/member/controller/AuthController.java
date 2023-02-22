package com.example.runi.member.controller;

import com.example.runi.member.domain.dto.LoginDto;
import com.example.runi.member.domain.dto.SignupDto;
import com.example.runi.member.service.AuthService;

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
    public String signUp(Model model, SignupDto reqeust) {

        //최초 빈값 세팅
        model.addAttribute("signupDto", reqeust);

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

        if (errors.hasErrors()) {

             /* 회원가입 실패시 입력 데이터 값을 유지 */
                model.addAttribute("signupDto", request);

                /* 유효성 통과 못한 필드와 메시지를 핸들링 */
                Map<String, String> validatorResult = authService.validateHandling(errors);
                for (String key : validatorResult.keySet()) {
                    model.addAttribute(key, validatorResult.get(key));
                }
                /* 회원가입 페이지로 다시 리턴 */
                return "member/signup";
        }

        authService.checkUsernameDuplication(request);

        authService.signup(request);
        
        return "redirect:/member/login";
    }
}

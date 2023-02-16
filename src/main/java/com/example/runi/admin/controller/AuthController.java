package com.example.runi.admin.controller;

import com.example.runi.admin.domain.dto.LoginDto;
import com.example.runi.admin.domain.dto.SignupDto;
import com.example.runi.admin.service.AuthService;
import lombok.AllArgsConstructor;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public String login(@Valid LoginDto request) {
        try {

            authService.login(request);

            return "redirect:/admin/dashboard";
        } catch (Exception e) {
            return "redirect:/admin/login";
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
                return "/admin/page/signup";
        }

        authService.checkUsernameDuplication(request);

        authService.signup(request);
        
        return "redirect:/admin/login";
    }
}

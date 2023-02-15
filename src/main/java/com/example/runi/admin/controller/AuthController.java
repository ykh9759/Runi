package com.example.runi.admin.controller;

import com.example.runi.admin.domain.dto.LoginDto;
import com.example.runi.admin.domain.dto.SignupDto;
import com.example.runi.admin.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestBody LoginDto request) {
        return "login";
    }

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public String signup(SignupDto request) {
        String id = authService.signup(request);

        if(!id.equals(request.getId()))
        {

        }
        
        return "redirect:/admin/login";
    }
}

package com.example.runi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.runi.config.MemberDetails;
import com.example.runi.domain.entity.LoginHistoryEntity;
import com.example.runi.domain.entity.MemberEntity;
import com.example.runi.service.MypageService;

@Controller
@RequestMapping("/member")
public class MypageController {

    private final MypageService mypageService;
    
    public MypageController(MypageService mypageService) {
        this.mypageService = mypageService;
    }

    @GetMapping("/login-history")
    public String index() {

        return "member/mypage/login-history";
    }


    @RequestMapping("/getLoginHistory")
    @ResponseBody
    public ResponseEntity<?> getLoginHistory(@AuthenticationPrincipal MemberDetails memberDetails) {


        List<LoginHistoryEntity> history = mypageService.getLoginHistory(memberDetails.getUserNo());


        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    @RequestMapping("/getMember")
    @ResponseBody
    public ResponseEntity<?> getMembEntity(@AuthenticationPrincipal MemberDetails memberDetails) {


        MemberEntity member = mypageService.getMember(memberDetails.getUserNo());


        return new ResponseEntity<>(member, HttpStatus.OK);
    }
    
}

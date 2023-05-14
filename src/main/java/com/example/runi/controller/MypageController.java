package com.example.runi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.runi.config.MemberDetails;
import com.example.runi.domain.dto.ProfileDto;
import com.example.runi.domain.entity.LoginHistoryEntity;
import com.example.runi.domain.entity.MemberEntity;
import com.example.runi.service.MypageService;
import com.example.runi.utils.Func;

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
    public ResponseEntity<?> getMember(@AuthenticationPrincipal MemberDetails memberDetails) {


        MemberEntity member = mypageService.getMember(memberDetails.getUserNo());


        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @RequestMapping("/updateMember")
    @ResponseBody
    public ResponseEntity<?> updateMember(@Valid ProfileDto request, Errors errors ,@AuthenticationPrincipal MemberDetails memberDetails) {

        System.out.println(request);


        Map<String, String> resultMap = new HashMap<String,String>();
        JSONObject result;

        if(!request.getPassword().equals(request.getRepeatPassword())) {

            resultMap.put("valid_repeatPassword", "비밀번호 확인 틀림");
            result = new JSONObject(resultMap);
            System.out.println(result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        //유효성체크
        if (errors.hasErrors()) {

            resultMap = Func.validateHandling(errors);

            resultMap.put("msg", "N");            
            result = new JSONObject(resultMap);
            System.out.println(result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }


        mypageService.updateMember(memberDetails.getUserNo(), request);
        resultMap.put("msg", "Y"); 
        result = new JSONObject(resultMap);


        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
}

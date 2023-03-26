package com.example.runi.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.example.runi.domain.entity.MemberEntity;
import com.example.runi.domain.entity.ProductEntity;
import com.example.runi.service.ProductService;
import com.example.runi.service.UserService;
import com.example.runi.utils.Func;
import com.example.runi.domain.dto.OrderDto;

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

    @GetMapping("/order")
    public String order(OrderDto request, Model model, RedirectAttributes redirectAttributes, HttpServletRequest hRequest) {

        System.out.println(hRequest);
        String id = request.getId();
        
        //아이디 체크
        boolean dupResult = userService.checkId(id);
        if(dupResult) {

            MemberEntity member = userService.getMember(id);
            request.setMemberNo(member.getNo());

            List<ProductEntity> products = userService.getProductMember(member.getNo());
            
            model.addAttribute("products", products);

            Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(hRequest);
            if(null != inputFlashMap) {
                OrderDto orderDto = (OrderDto)inputFlashMap.get("OrderDto");
                model.addAttribute("OrderDto", orderDto);
            } else {
                model.addAttribute("OrderDto", request);
            }
            return "user/order";
        } else {
            redirectAttributes.addFlashAttribute("error","존재하지 않는 아이디입니다.");
            return "redirect:/user";
        }
    }

    @PostMapping("/order-request")
    public String orderReqeust(@Valid OrderDto request, Errors errors, Model model, RedirectAttributes redirectAttributes) {
        System.out.println(request);

        //회원가입 실패시 입력 데이터 값을 유지
        redirectAttributes.addFlashAttribute("OrderDto", request);

        //유효성체크
        if (errors.hasErrors()) {

            Map<String, String> validatorResult = Func.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                redirectAttributes.addFlashAttribute(key, validatorResult.get(key));
            }

            return "redirect:/user/order?id="+request.getId();
        }

        //중복체크
        Map<String, String> dupResult = userService.checkDuplication(request);
        if(!dupResult.isEmpty()) {

            for (String key : dupResult.keySet()) {

                redirectAttributes.addFlashAttribute(key, dupResult.get(key));
            }

            return "redirect:/user/order?id="+request.getId();
        }

        userService.orderSave(request);
        
        redirectAttributes.addFlashAttribute("order","주문완료");
        return "redirect:/user";
    }

}

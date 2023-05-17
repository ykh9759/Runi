package com.example.runi.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.runi.config.MemberDetails;
import com.example.runi.service.DashboardService;


@Controller
@RequestMapping("/member")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("")
    public String index(Model model, @AuthenticationPrincipal MemberDetails memberDetails) {

        Integer memberNo = memberDetails.getUserNo();


        int monthSales = dashboardService.getMonthSlaes(memberNo);
        int yearSales = dashboardService.getYearSlaes(memberNo);
        int ReceiptCnt = dashboardService.getReceiptOrderCnt(memberNo);
        int SuccessCnt = dashboardService.getSuccessOrderCnt(memberNo);

        model.addAttribute("monthSales", String.valueOf(monthSales) + "원");
        model.addAttribute("yearSales", String.valueOf(yearSales) + "원");
        model.addAttribute("ReceiptCnt", String.valueOf(ReceiptCnt) + "건");
        model.addAttribute("SuccessCnt", String.valueOf(SuccessCnt) + "건");


        return "member/dashboard";
    }

}

package com.example.runi.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.runi.config.GlobalValue;
import com.example.runi.repository.OrderListRepository;
import com.example.runi.repository.OrderRepository;

@Service
public class DashboardService {

    private final OrderRepository orderRepository;
    private final OrderListRepository orderListRepository;
    private final GlobalValue globalValue;

    public DashboardService(OrderRepository orderRepository, OrderListRepository orderListRepository, GlobalValue globalValue) {
        this.orderRepository = orderRepository;
        this.orderListRepository = orderListRepository;
        this.globalValue = globalValue;
        
    }

    //이번달매출
    public int getMonthSlaes(Integer memberNo) {

        LocalDate now = LocalDate.now();  // 현재 날짜 가져오기
        LocalDate firstDayOfMonth = now.withDayOfMonth(1);  //이번달 첫째날 가져오기
        LocalDate lastDayOfMonth = now.withDayOfMonth(now.lengthOfMonth());  // 현재 월의 마지막 날짜 가져오기

        int monthSales = orderListRepository.getMonthSales(memberNo, firstDayOfMonth, lastDayOfMonth);

        return monthSales;
    }

    //올해매출
    public int getYearSlaes(Integer memberNo) {

        LocalDate now = LocalDate.now();  // 현재 날짜 가져오기
        LocalDate firstDayOfYear = now.withDayOfYear(1); //올해 첫째날 가져오기
        LocalDate lastDayOfYear = now.withDayOfYear(now.lengthOfYear()); //올해 마지막날 가져오기

        int monthSales = orderListRepository.getYearSales(memberNo, firstDayOfYear, lastDayOfYear);

        return monthSales;
    }

    //주문요청건수
    public int getReceiptOrderCnt(Integer memberNo) {

        LocalDate now = LocalDate.now();  // 현재 날짜 가져오기
        LocalDate firstDayOfYear = now.withDayOfYear(1); //올해 첫째날 가져오기
        LocalDate lastDayOfYear = now.withDayOfYear(now.lengthOfYear()); //올해 마지막날 가져오기

        int monthSales = orderListRepository.getReceiptOrderCnt(memberNo);

        return monthSales;
    }

    //주문완료건수
    public int getSuccessOrderCnt(Integer memberNo) {

        LocalDate now = LocalDate.now();  // 현재 날짜 가져오기
        LocalDate firstDayOfYear = now.withDayOfYear(1); //올해 첫째날 가져오기
        LocalDate lastDayOfYear = now.withDayOfYear(now.lengthOfYear()); //올해 마지막날 가져오기

        int monthSales = orderListRepository.getSuccessOrderCnt(memberNo);

        return monthSales;
    }
    
}

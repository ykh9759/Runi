package com.example.runi.domain.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderDto {
    
    private String name;
    private String phone;
    private String email;
    private String address;
    private String accountName;
    private List<Integer> productNo;
    private List<Integer> productCnt;
    private String parcel;
    private String cashReceipts;
}

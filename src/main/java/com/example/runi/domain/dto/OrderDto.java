package com.example.runi.domain.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.example.runi.domain.entity.OrderListEntity;

import lombok.Data;

@Data
public class OrderDto {

    private String id;
    private Integer memberNo;
    
    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @NotBlank(message = "휴대폰번호를 입력해주세요")
    private String phone;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email
    private String email;

    @NotBlank(message = "주소를 입력해주세요")
    private String address;

    @NotBlank(message = "입금자명을 입력해주세요")
    private String accountName;
    
    private List<Integer> productNo;
    private List<Integer> productCnt;

    @NotBlank(message = "배송방식을 선택해주세요")
    private String parcel;
    
    private String cashReceipts;

}

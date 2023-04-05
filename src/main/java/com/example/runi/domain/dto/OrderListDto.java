package com.example.runi.domain.dto;

import java.time.LocalDate;

import com.example.runi.domain.entity.OrderListEntity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderListDto {


    private Integer no;

    private Integer memberNo;

    private String name;                    //이름
    private String phone;                   //휴대폰번호
    private String email;                   //이메일
    private String address;                 //주소

    private String accountName;             //계좌이름

    private String parcel;                  //배송방법
    private String cashReceipts;            //현금영수증유무

    private String plist;                    //현금영수증유무
    private Integer price;                    //현금영수증유무

    private LocalDate inTime;                    //현금영수증유무

    @Builder
    public OrderListDto(OrderListEntity entity) {
        no = entity.getNo();
        memberNo = entity.getMemberNo();
        name = entity.getName();

        phone = entity.getPhone();
        phone = phone.substring(0, 3)+ "-" + phone.substring(3, 7) + "-" +  phone.substring(7, 11);

        email = entity.getEmail();
        address = entity.getAddress();
        accountName = entity.getAccountName();
        parcel = entity.getParcel();
        cashReceipts = entity.getCashReceipts();
        inTime = entity.getInTime().toLocalDate();
        plist = entity.getPlist();

        price = entity.getPrice();
    }

}

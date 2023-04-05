package com.example.runi.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;

    @Column(name = "member_no")
    private Integer memberNo;

    private String name;                    //이름
    private String phone;                   //휴대폰번호
    private String email;                   //이메일
    private String address;                 //주소

    @Column(name = "account_name")
    private String accountName;             //계좌이름

    private String parcel;                  //배송방법
    private String cashReceipts;            //현금영수증유무

    private String plist;                    //현금영수증유무
    private Integer price;                    //현금영수증유무

    @Column(name = "insert_time")
    private LocalDateTime inTime;                    //현금영수증유무

    @Builder
    public OrderListEntity(OrderEntity entity, String plist, Integer price) {
        no = entity.getNo();
        memberNo = entity.getMemberNo();
        name = entity.getName();
        phone = entity.getPhone();
        email = entity.getEmail();
        address = entity.getAddress();
        accountName = entity.getAccountName();
        parcel = entity.getParcel();
        cashReceipts = entity.getCashReceipts();
        inTime = entity.getInTime();
        this.plist = plist;
        this.price = price;
    }

}

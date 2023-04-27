package com.example.runi.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.example.runi.domain.dto.OrderDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "orders")
public class OrderEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;

    @Column(name = "member_no")
    private Integer memberNo;

    private String name;                    //이름
    private String phone;                   //휴대폰번호
    private String email;                   //이메일

    @Column(name = "zip_code")
    private String zipCode;                 //주소

    private String address;                 //주소
    private String address2;                 //주소

    @Column(name = "account_name")
    private String accountName;             //계좌이름

    private String parcel;                  //배송방법
    private String cashReceipts;            //현금영수증유무
    private String status;                  //주문상태

    @OneToMany(mappedBy = "oNo")
    private List<OrderProductEntity> refoNo = new ArrayList<>();
    
    @Builder
    public OrderEntity(OrderDto dto) {
        memberNo = dto.getMemberNo();
        name = dto.getName();
        phone = dto.getPhone();
        email = dto.getEmail();
        zipCode = dto.getZipCode();
        address = dto.getAddress();
        address2 = dto.getAddress2();
        accountName = dto.getAccountName();
        parcel = dto.getParcel();
        cashReceipts = dto.getCashReceipts();
        status = dto.getStatus();
    }

    public void statusUpdate(String status) {
        this.status = status;
    }
}

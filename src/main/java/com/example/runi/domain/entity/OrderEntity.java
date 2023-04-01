package com.example.runi.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.example.runi.domain.dto.OrderDto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private String address;                 //주소

    @Column(name = "account_name")
    private String accountName;             //계좌이름

    private String parcel;                  //배송방법
    private String cashReceipts;            //현금영수증유무

    @OneToMany(mappedBy = "oNo")
    private List<OrderProductEntity> orderProductEntities = new ArrayList<>();
    
    @Builder
    public OrderEntity(OrderDto dto) {
        memberNo = dto.getMemberNo();
        name = dto.getName();
        phone = dto.getPhone();
        email = dto.getEmail();
        address = dto.getAddress();
        accountName = dto.getAccountName();
        parcel = dto.getParcel();
        cashReceipts = dto.getCashReceipts();
    }
}

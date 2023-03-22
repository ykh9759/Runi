package com.example.runi.domain.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.example.runi.domain.dto.OrderDto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "order_list")
public class OrderListEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;

    @Column(name = "member_id")
    private String memberId;

    private String name;
    private String phone;
    private String email;
    private String address;

    @Column(name = "account_name")
    private String accountName;

    private Integer price;

    private String parcel;
    private String cashReceipts;

    
    @Builder
    public OrderListEntity(OrderDto dto, Integer pNo, Integer pCnt) {
        memberId = dto.getId();
        name = dto.getName();
        phone = dto.getPhone();
        email = dto.getEmail();
        address = dto.getAddress();
    }
}

package com.example.runi.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.runi.domain.dto.LoginDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "login_history")
public class LoginHistoryEntity  extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer no;

    @Column(name = "member_no")
    Integer memberNo;

    String ip;

    @Builder
    public LoginHistoryEntity(Integer memberNo, String ip) {
       this.memberNo = memberNo;
       this.ip = ip;
    }
    
}

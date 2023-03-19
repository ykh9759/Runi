package com.example.runi.domain.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.runi.domain.dto.SignupDto;
import com.example.runi.config.Role;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "member")
public class MemberEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;

    private String name;
    private String id;
    private String password;

    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public MemberEntity(SignupDto request) {
        name = request.getName();
        email = request.getEmail();
        password = request.getPassword();
        id = request.getId();
        role = Role.ADMIN;
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }
    
}

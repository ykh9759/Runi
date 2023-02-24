package com.example.runi.member.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.runi.global.config.Role;
import com.example.runi.member.domain.dto.SignupDto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "member")
public class MemberEntity {

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

    @CreationTimestamp
	@Column(name = "insert_date")
	private LocalDateTime inDate;

    @UpdateTimestamp
	@Column(name = "update_date")
	private LocalDateTime upDate;

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

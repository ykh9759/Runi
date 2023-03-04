package com.example.runi.member.domain.entity;

import com.example.runi.member.domain.dto.ProductDto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "product")
public class ProductEntity extends BaseTimeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;

    @Column(name = "member_no")
    private Integer memberNo;

    @Column(name = "product_name")
    private String productName;

    private Integer price;

    @CreatedDate
    @Column(name = "save_date")
    private LocalDate saveDate;
 
    @Builder
    public ProductEntity(ProductDto request) {
        memberNo = request.getMemberNo();
        productName = request.getProductName();
        price = request.getPrice();
    }
}

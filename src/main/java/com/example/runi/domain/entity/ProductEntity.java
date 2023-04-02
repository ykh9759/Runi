package com.example.runi.domain.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;

import com.example.runi.domain.dto.ProductDto;

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

    @OneToMany(mappedBy = "pNo")
    private List<OrderProductEntity> refpNo = new ArrayList<>();
 
    @Builder
    public ProductEntity(ProductDto dto) {
        memberNo = dto.getMemberNo();
        productName = dto.getProductName();
        price = dto.getPrice();
    }
}

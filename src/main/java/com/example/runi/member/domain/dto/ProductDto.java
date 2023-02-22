package com.example.runi.member.domain.dto;

import com.example.runi.member.domain.entity.ProductEntity;

import lombok.Data;

@Data
public class ProductDto {
 
    private Integer memberNo;
    private String productName;
    private String price;

    public ProductEntity toEntity() {
        return ProductEntity.builder()
                .request(this)
                .build();
    }
}

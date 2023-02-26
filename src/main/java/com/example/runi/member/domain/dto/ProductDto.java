package com.example.runi.member.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.runi.member.domain.entity.ProductEntity;

import lombok.Data;

@Data
public class ProductDto {
 
    private Integer memberNo;

    @NotBlank(message = "상품명을 입력해주세요")
    private String productName;

    @NotNull(message = "가격을 입력해주세요")
    private Integer price;

    public ProductEntity toEntity() {
        return ProductEntity.builder()
                .request(this)
                .build();
    }
}

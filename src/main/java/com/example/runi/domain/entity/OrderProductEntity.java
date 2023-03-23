package com.example.runi.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "order_product")
@IdClass(OrderProductPK.class)
public class OrderProductEntity {

    @Id
    @Column(name = "o_no")
    private Integer oNo;

    @Id
    @Column(name = "p_no")
    private Integer pNo;

    @Column(name = "p_cnt")
    private Integer pCnt;

    @Builder
    public OrderProductEntity(Integer oNo, Integer pNo, Integer pCnt) {
        this.oNo = oNo;
        this.pNo = pNo;
        this.pCnt = pCnt;
    }
    
}

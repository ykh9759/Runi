package com.example.runi.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;


import javax.persistence.JoinColumn;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "order_product")
@IdClass(OrderProductPK.class)
public class OrderProductEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "o_no", referencedColumnName = "no")
    private OrderEntity oNo;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_no", referencedColumnName = "no")
    private ProductEntity pNo;

    @Column(name = "p_cnt")
    private Integer pCnt;

    @Column(name = "p_price")
    private Integer pPrice;

    @Builder
    public OrderProductEntity(OrderEntity oNo, ProductEntity pNo, Integer pCnt, Integer pPrice) {
        this.oNo = oNo;
        this.pNo = pNo;
        this.pCnt = pCnt;
        this.pPrice = pPrice;
    }
    
}

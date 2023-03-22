package com.example.runi.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ManyToAny;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "order_product")
public class OrderProductEntity {

    @Column(name = "o_no")
    private Integer oNo;

    @Column(name = "p_no")
    private Integer pNo;

    @Column(name = "p_cnt")
    private Integer pcnt;
    
}

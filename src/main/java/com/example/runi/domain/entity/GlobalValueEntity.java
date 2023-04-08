package com.example.runi.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "global_value")
public class GlobalValueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer no;

    String category;

    String code;

    String value;
    
}

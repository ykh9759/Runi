package com.example.runi.member.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;


import static com.example.runi.member.domain.entity.QProductEntity.product;

import com.example.runi.member.domain.dto.SearchDto;
import com.example.runi.member.domain.entity.ProductEntity;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueryDSL {
    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.queryFactory = jpaQueryFactory;
    }

    public List<ProductEntity> findBySearch(Integer memberNo, SearchDto reqeust) {

        return queryFactory
                    .selectFrom(product)
                    .where(
                        product.memberNo.eq(memberNo),
                        select(reqeust), 
                        date(reqeust)
                    )
                    .orderBy(product.no.desc())
                    .fetch();
    }

    private BooleanExpression select(SearchDto reqeust) {
        
        String select = reqeust.getSelect();
        String search = reqeust.getSearch();

        if(search.isEmpty()) return null;

        try {

            if(select.equals("0")) {
                return product.no.eq(Integer.parseInt(search));
            } else if(select.equals("1")) {
                return product.productName.eq(search);
            } else if(select.equals("2")) {
                return product.price.eq(Integer.parseInt(search));
            } else if(select.equals("3")) {
                return product.saveDate.eq(LocalDate.parse(search));
            } else {
                return null;
            }

        } catch(Exception e) {
            return null;
        }
    }
    

    //날짜 검색
    private BooleanExpression date(SearchDto reqeust) {
        
        String startDate = reqeust.getStartDate();
        String endDate = reqeust.getEndDate();

        if(!startDate.isEmpty() && endDate.isEmpty()) {
            return product.saveDate.goe(LocalDate.parse(startDate));
        }else if(startDate.isEmpty() && !endDate.isEmpty()) {
            return product.saveDate.loe(LocalDate.parse(endDate));
        }else if(!startDate.isEmpty() || !endDate.isEmpty()) {
            return product.saveDate.between(LocalDate.parse(startDate), LocalDate.parse(endDate));
        }else { 
            return null;
        }
    }
    
}

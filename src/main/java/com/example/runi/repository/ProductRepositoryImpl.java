package com.example.runi.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.runi.domain.dto.SearchDto;
import com.example.runi.domain.entity.ProductEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;


import static com.example.runi.domain.entity.QProductEntity.productEntity;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQDSL {
    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public List<ProductEntity> findBySearch(SearchDto reqeust, Integer memberNo) {

        return queryFactory
                    .selectFrom(productEntity)
                    .where(
                        productEntity.memberNo.eq(memberNo),
                        productEntity.saveStatus.eq("Y"),
                        select(reqeust), 
                        date(reqeust)
                    )
                    .orderBy(productEntity.no.desc())
                    .fetch();
    }

    private BooleanExpression select(SearchDto reqeust) {
        
        Optional<String> select = Optional.ofNullable(reqeust.getSelect().trim());
        Optional<String> search = Optional.ofNullable(reqeust.getSearch().trim());

        if(!search.isPresent() || search.get().isEmpty()) return null;


        if(select.get().equals("0")) {
            return productEntity.no.like("%" + Integer.parseInt(search.get()) + "%");
        } else if(select.get().equals("1")) {
            return productEntity.productName.like("%" + search.get() + "%");
        } else if(select.get().equals("2")) {
            return productEntity.price.like("%" + Integer.parseInt(search.get()) + "%");
        } else if(select.get().equals("3")) {
            return productEntity.saveDate.eq(LocalDate.parse(search.get()));
        } else {
            return null;
        }
    }
    

    //날짜 검색
    private BooleanExpression date(SearchDto reqeust) {
        
        Optional<String> startDate = Optional.ofNullable(reqeust.getStartDate().trim());
        Optional<String> endDate = Optional.ofNullable(reqeust.getEndDate().trim());

        if(startDate.isPresent() && !endDate.isPresent()) {
            return productEntity.saveDate.goe(LocalDate.parse(startDate.get()));
        }else if(!startDate.isPresent() && endDate.isPresent()) {
            return productEntity.saveDate.loe(LocalDate.parse(endDate.get()));
        }else if((startDate.isPresent() && !startDate.get().isEmpty()) && (endDate.isPresent() && !endDate.get().isEmpty())) {
            return productEntity.saveDate.between(LocalDate.parse(startDate.get()), LocalDate.parse(endDate.get()));
        }else { 
            return null;
        }
    }
    
}

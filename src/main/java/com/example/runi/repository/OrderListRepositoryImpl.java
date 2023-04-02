package com.example.runi.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.runi.domain.dto.SearchDto;
import com.example.runi.domain.entity.OrderEntity;
import com.example.runi.domain.entity.OrderListEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.example.runi.domain.entity.QOrderEntity.orderEntity;
import static com.example.runi.domain.entity.QOrderProductEntity.orderProductEntity;
import static com.example.runi.domain.entity.QOrderListEntity.orderListEntity;

@Repository
public class OrderListRepositoryImpl implements OrderListRepositoryQDSL {

    private final JPAQueryFactory queryFactory;

    public OrderListRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public List<OrderListEntity> findBySearch(SearchDto reqeust, Integer memberNo) {
        
        return queryFactory
                        .selectFrom(orderListEntity)
                        // .rightJoin(orderEntity.orderProductEntities ,orderProductEntity)
                        .orderBy(orderEntity.no.desc())
                        .fetch();
    }

    // private BooleanExpression select(SearchDto reqeust) {
        
    //     String select = reqeust.getSelect();
    //     String search = reqeust.getSearch();

    //     if(search.isEmpty()) return null;

    //     try {

    //         if(select.equals("0")) {
    //             return OrderEntity.no.eq(Integer.parseInt(search));
    //         } else if(select.equals("1")) {
    //             return OrderEntity.name.eq(search);
    //         } else if(select.equals("2")) {
    //             return orderEntity.phone.eq(search);
    //         } else if(select.equals("3")) {
    //             return orderEntity.address.eq(search);
    //         } else if(select.equals("4")) {
    //             return orderEntity.accountName.eq(search);
    //         }else if(select.equals("5")) {
    //             return orderEntity.parcel.eq(search);
    //         }else if(select.equals("6")) {
    //             return orderEntity.cashReceipts.eq(search);
    //         }else if(select.equals("7")) {
    //             return orderEntity.orderProductEntities.get().eq(Integer.parseInt(search));
    //         }else {
    //             return null;
    //         }

    //     } catch(Exception e) {
    //         return null;
    //     }
    // }
    

    // //날짜 검색
    // private BooleanExpression date(SearchDto reqeust) {
        
    //     String startDate = reqeust.getStartDate();
    //     String endDate = reqeust.getEndDate();

    //     if(!startDate.isEmpty() && endDate.isEmpty()) {
    //         return orderEntity.inTime.goe(LocalDateTime.parse(startDate));
    //     }else if(startDate.isEmpty() && !endDate.isEmpty()) {
    //         return orderEntity.inTime.loe(LocalDateTime.parse(endDate));
    //     }else if(!startDate.isEmpty() || !endDate.isEmpty()) {
    //         return orderEntity.inTime.between(LocalDateTime.parse(startDate), LocalDateTime.parse(endDate));
    //     }else { 
    //         return null;
    //     }
    // }

}

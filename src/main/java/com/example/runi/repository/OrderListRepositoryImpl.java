package com.example.runi.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.runi.domain.dto.SearchDto;
import com.example.runi.domain.entity.OrderListEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.example.runi.domain.entity.QOrderEntity.orderEntity;;
import static com.example.runi.domain.entity.QOrderProductEntity.orderProductEntity;;

@Repository
public class OrderListRepositoryImpl implements OrderListRepositoryQDSL {

    private final JPAQueryFactory queryFactory;

    public OrderListRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public List<OrderListEntity> findBySearch(SearchDto reqeust, Integer memberNo) {
        
        return queryFactory
                        .selectFrom(orderEntity)
                        .leftJoin(orderProductEntity)
                        .on(orderEntity.no.eq(Integer.parseInt(orderProductEntity.oNo.toString())))
                        .where(
                            orderEntity.memberNo.eq(memberNo),
                            select(reqeust), 
                            date(reqeust)
                        )
                        .orderBy(orderListEntity.no.desc())
                        .fetch();
    }

    private BooleanExpression select(SearchDto reqeust) {
        
        String select = reqeust.getSelect();
        String search = reqeust.getSearch();

        if(search.isEmpty()) return null;

        try {

            if(select.equals("0")) {
                return orderListEntity.no.eq(Integer.parseInt(search));
            } else if(select.equals("1")) {
                return orderListEntity.name.eq(search);
            } else if(select.equals("2")) {
                return orderListEntity.phone.eq(search);
            } else if(select.equals("3")) {
                return orderListEntity.address.eq(search);
            } else if(select.equals("4")) {
                return orderListEntity.accountName.eq(search);
            }else if(select.equals("5")) {
                return orderListEntity.parcel.eq(search);
            }else if(select.equals("6")) {
                return orderListEntity.cashReceipts.eq(search);
            }else if(select.equals("7")) {
                return orderListEntity.price.eq(Integer.parseInt(search));
            }else {
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
            return orderListEntity.inTime.goe(LocalDateTime.parse(startDate));
        }else if(startDate.isEmpty() && !endDate.isEmpty()) {
            return orderListEntity.inTime.loe(LocalDateTime.parse(endDate));
        }else if(!startDate.isEmpty() || !endDate.isEmpty()) {
            return orderListEntity.inTime.between(LocalDateTime.parse(startDate), LocalDateTime.parse(endDate));
        }else { 
            return null;
        }
    }

}

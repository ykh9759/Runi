package com.example.runi.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.runi.domain.dto.SearchDto;
import com.example.runi.domain.entity.OrderEntity;
import com.example.runi.domain.entity.OrderListEntity;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.example.runi.domain.entity.QOrderEntity.orderEntity;
import static com.example.runi.domain.entity.QOrderProductEntity.orderProductEntity;
import static com.example.runi.domain.entity.QProductEntity.productEntity;

@Repository
public class OrderListRepositoryImpl implements OrderListRepositoryQDSL {

    private final JPAQueryFactory queryFactory;

    public OrderListRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public List<OrderListEntity> findBySearch(SearchDto request, Integer memberNo) {

        System.out.println("lmpl : " + request);

        List<Tuple> list =  queryFactory
						.select(
							orderEntity,
							Expressions.stringTemplate( "ARRAY_TO_STRING(ARRAY_AGG({0}||{1}||'개'),'<br>')", productEntity.productName, orderProductEntity.pCnt).as("plist"),
							orderProductEntity.pPrice.sum().as("price")
						)
						.from(orderEntity)
                        .leftJoin(orderEntity.refoNo, orderProductEntity)
						.leftJoin(orderProductEntity.pNo, productEntity)
						.where(
                            orderEntity.memberNo.eq(memberNo),
                            orderEntity.status.eq(request.getStatus()),
                            searchWhere(request),
                            date(request)
                        ) 
                        .orderBy(orderEntity.no.desc())
						.groupBy(orderEntity.no)
                        .having(searchHaving(request))
						.fetch();

        List<OrderListEntity> listole = new ArrayList<>();

        for(Tuple tuple : list) {
            
            OrderListEntity orderListEntity = OrderListEntity.builder()
                                                        .entity(tuple.get(0, OrderEntity.class))
                                                        .plist(tuple.get(1, String.class))
                                                        .price(tuple.get(2, Integer.class))
                                                        .build();
            listole.add(orderListEntity);
        }

        return listole;
    }

    private BooleanExpression searchWhere(SearchDto request) {
        
        Optional<String> select = Optional.ofNullable(request.getSelect().trim());
        Optional<String> search = Optional.ofNullable(request.getSearch().trim());

        if(!select.isPresent() || select.get().isEmpty()) { 
            return null;
        } else if(!search.isPresent() || search.get().isEmpty()) {
            return null;
        }

        try {
            if (select.get().equals("0")) {
                return orderEntity.no.like("%" + Integer.parseInt(search.get()) + "%" );
            } else if (select.get().equals("1")) {
                return orderEntity.name.like("%" + search.get() + "%");
            } else if (select.get().equals("2")) {
                return orderEntity.phone.like("%" + search.get() + "%");
            } else if (select.get().equals("3")) {
                return orderEntity.address.like("%" + search.get() + "%");
            } else if (select.get().equals("4")) {
                return orderEntity.accountName.like("%" + search.get() + "%");
            } else if (select.get().equals("5")) {
                return orderEntity.parcel.like("%" + search.get() + "%");
            } else if (select.get().equals("6")) {
                return orderEntity.cashReceipts.like("%" + search.get() + "%");
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("INT 파싱에러");
            return null;
        }

    }
    

    //날짜 검색
    private BooleanExpression date(SearchDto request) {

        Optional<String> sDate = Optional.ofNullable(request.getStartDate().trim());
        Optional<String> eDate = Optional.ofNullable(request.getEndDate().trim());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Optional<LocalDateTime> startDate = Optional.empty();
        Optional<LocalDateTime> endDate = Optional.empty();
        
        System.out.println(sDate);
        System.out.println(eDate);
        
        if(sDate.isPresent() && !sDate.get().isEmpty()) {
            startDate =  Optional.ofNullable(LocalDate.parse(sDate.get(), formatter).atStartOfDay());
        } 

        if(eDate.isPresent() && !eDate.get().isEmpty()) {
            endDate = Optional.ofNullable(LocalDate.parse(eDate.get(), formatter).atStartOfDay().plusDays(1));
        }


        if(startDate.isPresent() && !endDate.isPresent()) {
            return orderEntity.inTime.goe(startDate.get());
        }else if(!startDate.isPresent() && endDate.isPresent()) {
            return orderEntity.inTime.loe(endDate.get());
        }else if(startDate.isPresent() && endDate.isPresent()) {
            return orderEntity.inTime.between(startDate.get(), endDate.get());
        }else { 
            return null;
        }
    }

    private BooleanExpression searchHaving(SearchDto request) {

        Optional<String> select = Optional.ofNullable(request.getSelect().trim());
        Optional<String> search = Optional.ofNullable(request.getSearch().trim());

        try {
            if(select.get().equals("7")) {
                return Expressions.stringTemplate( "ARRAY_TO_STRING(ARRAY_AGG({0}||{1}||'개'),'<br>')", productEntity.productName, orderProductEntity.pCnt).like("%" + search.get() + "%");
            } else if(select.get().equals("8")) {
                return orderProductEntity.pPrice.sum().like("%" + Integer.parseInt(search.get()) + "%");
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("INT 파싱에러");
            return null;
        }

    }

}

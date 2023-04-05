package com.example.runi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.runi.domain.dto.SearchDto;
import com.example.runi.domain.entity.OrderEntity;
import com.example.runi.domain.entity.OrderListEntity;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.example.runi.domain.entity.QOrderEntity.orderEntity;
import static com.example.runi.domain.entity.QOrderProductEntity.orderProductEntity;
import static com.example.runi.domain.entity.QProductEntity.productEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.transaction.Transactional;

@SpringBootTest
class RuniApplicationTests {

	@Autowired
	private JPAQueryFactory queryFactory;


	@Test
	@Transactional
    public void findBySearch() {

		SearchDto request = new SearchDto();

		Integer memberNo = 7;
		request.setSelect("0");
		request.setSearch("5");
        request.setStartDate("2023-03-27");
		
	

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

    }


    private BooleanExpression searchWhere(SearchDto request) {
        
        String select = request.getSelect();
        String search = request.getSearch();

        if(search.isEmpty()) return null;

        try {

            if(select.equals("0")) {
                return orderEntity.no.eq(Integer.parseInt(search));
            } else if(select.equals("1")) {
                return orderEntity.name.eq(search);
            } else if(select.equals("2")) {
                return orderEntity.phone.eq(search);
            } else if(select.equals("3")) {
                return orderEntity.address.eq(search);
            } else if(select.equals("4")) {
                return orderEntity.accountName.eq(search);
            }else if(select.equals("5")) {
                return orderEntity.parcel.eq(search);
            }else if(select.equals("6")) {
                return orderEntity.cashReceipts.eq(search);
            }else {
                return null;
            }

        } catch(Exception e) {
            return null;
        }
    }
    

    //날짜 검색
    private BooleanExpression date(SearchDto request) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        System.out.println(LocalDate.parse(request.getStartDate(), formatter));
        
        LocalDateTime startDate = LocalDate.parse(request.getStartDate(), formatter).atStartOfDay();
        LocalDateTime endDate = LocalDate.parse(request.getEndDate(), formatter).atStartOfDay();

        if(startDate != null && endDate == null) {
            return orderEntity.inTime.goe(startDate);
        }else if(startDate == null && endDate != null) {
            return orderEntity.inTime.loe(endDate);
        }else if(startDate != null || endDate != null) {
            return orderEntity.inTime.between(startDate, endDate);
        }else { 
            return null;
        }
    }

    private BooleanExpression searchHaving(SearchDto request) {

        String select = request.getSelect();
        String search = request.getSearch();

        try {

            if(select.equals("7")) {
                return Expressions.stringTemplate( "ARRAY_TO_STRING(ARRAY_AGG({0}||{1}||'개'),'<br>')", productEntity.productName, orderProductEntity.pCnt).eq(search);
            } else if(select.equals("8")) {
                return orderProductEntity.pPrice.sum().eq(Integer.parseInt(search));
            } else {
                return null;
            }

        } catch(Exception e) {
            return null;
        }
    }

}

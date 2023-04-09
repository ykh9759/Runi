package com.example.runi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.runi.config.GlobalValue;
import com.example.runi.domain.dto.SearchDto;
import com.example.runi.domain.entity.GlobalValueEntity;
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
import java.util.Map;
import java.util.Optional;

import javax.persistence.Entity;
import javax.transaction.Transactional;

@SpringBootTest
class RuniApplicationTests {

	@Autowired
	private JPAQueryFactory queryFactory;

    @Autowired
    private GlobalValue globalValue;

    @Test 
    public void test() {

        String category = "parcel";

        
        String value = globalValue.getGlobalValue(category, "1");

        System.out.println(value);
        

        
    }


	@Test
	@Transactional
    public void findBySearch() {

		SearchDto request = new SearchDto();

		Integer memberNo = 7;
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
        
        Optional<String> select = Optional.ofNullable(request.getSelect().trim());
        Optional<String> search = Optional.ofNullable(request.getSearch().trim());

        if(!select.isPresent()) { 
            return null;
        } else if(!search.isPresent()) {
            return null;
        }

        String str1 = select.get();
        String str2 = search.get();

        if (str1.equals("0")) {
            return orderEntity.no.eq(Integer.parseInt(str2));
        } else if (str1.equals("1")) {
            return orderEntity.name.eq(str2);
        } else if (str1.equals("2")) {
            return orderEntity.phone.eq(str2);
        } else if (str1.equals("3")) {
            return orderEntity.address.eq(str2);
        } else if (str1.equals("4")) {
            return orderEntity.accountName.eq(str2);
        } else if (str1.equals("5")) {
            return orderEntity.parcel.eq(str2);
        } else if (str1.equals("6")) {
            return orderEntity.cashReceipts.eq(str2);
        } else {
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
        
        
        if(sDate.isPresent()) {
            startDate =  Optional.ofNullable(LocalDate.parse(sDate.get(), formatter).atStartOfDay());
        }

        if(eDate.isPresent()) {
            endDate = Optional.ofNullable(LocalDate.parse(eDate.get(), formatter).atStartOfDay());
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

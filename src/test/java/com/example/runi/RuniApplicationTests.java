package com.example.runi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.runi.domain.dto.SearchDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.example.runi.domain.entity.QOrderEntity.orderEntity;
import static com.example.runi.domain.entity.QOrderProductEntity.orderProductEntity;
import static com.example.runi.domain.entity.QProductEntity.productEntity;
import static com.example.runi.domain.entity.QOrderListEntity.orderListEntity;

import java.util.List;

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
		
	

		List<Tuple> a =  queryFactory
						.select(
							orderEntity.no,
							Expressions.template(String.class, "array_agg({0})", productEntity.productName).as("plist"),
							orderProductEntity.pPrice.sum().as("price")
						)
						.from(orderEntity)
                        .leftJoin(orderEntity.refoNo, orderProductEntity)
						.leftJoin(orderProductEntity.pNo, productEntity)
						.where(orderEntity.memberNo.eq(memberNo)) 
                        .orderBy(orderEntity.no.desc())
						.groupBy(orderEntity.no)
						.fetch();
						



		System.out.print(a);
    }


	//  private BooleanExpression select(SearchDto request) {
        
    //     String select = request.getSelect();
    //     String search = request.getSearch();

    //     if(search.isEmpty()) return null;

    //     try {

    //         if(select.equals("0")) {
    //             return orderEntity.no.eq(Integer.parseInt(search));
    //         } else if(select.equals("1")) {
    //             return orderEntity.name.eq(search);
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
    //             return orderEntity.orderProductEntities.search);
    //         }else if(select.equals("8")) {
    //             return orderEntity.orderProductEntities.;
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

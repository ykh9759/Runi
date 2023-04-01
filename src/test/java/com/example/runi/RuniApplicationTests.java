package com.example.runi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.runi.domain.dto.SearchDto;
import com.example.runi.domain.entity.OrderEntity;
import com.example.runi.domain.entity.OrderListEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.example.runi.domain.entity.QOrderEntity.orderEntity;
import static com.example.runi.domain.entity.QOrderProductEntity.orderProductEntity;

import java.util.List;

import javax.transaction.Transactional;

import static com.example.runi.domain.entity.QOrderListEntity.orderListEntity;

@SpringBootTest
class RuniApplicationTests {

	@Autowired
	private JPAQueryFactory queryFactory;

    public List<OrderEntity> findBySearch(SearchDto reqeust, Integer memberNo) {
        
		return queryFactory.selectFrom(orderEntity)
                        .rightJoin(orderEntity.orderProductEntities ,orderProductEntity)
                        .orderBy(orderEntity.no.desc())
                        .fetch();
    }

	@Test
	@Transactional
    public void join() {

		SearchDto reqeust = new SearchDto();

		Integer memberNo = 7;
		reqeust.setSelect("0");
		reqeust.setSearch("5");
        
		List<OrderEntity> entity = findBySearch(reqeust, memberNo);

		System.out.println(entity.get(0).getOrderProductEntities().get(0).getPCnt());
	}

}

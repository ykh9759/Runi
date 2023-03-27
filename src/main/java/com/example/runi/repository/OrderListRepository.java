package com.example.runi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.runi.domain.entity.OrderListEntity;

public interface OrderListRepository extends JpaRepository<OrderListEntity, Integer>{

    @Query(value = "SELECT * FROM orders o LEFT JOIN (SELECT o_no, ARRAY_TO_STRING(ARRAY_AGG(p.product_name||CAST(op.p_cnt AS TEXT)||'개'),'<br>') as plist, sum(op.p_price) as price FROM order_product op LEFT JOIN product p on op.p_no = p.no GROUP BY o_no) ops ON o.no = ops.o_no WHERE o.member_no = :memberNo", nativeQuery = true)
    public List<OrderListEntity> findByMemberNoOrderByNoDesc(@Param("memberNo") Integer memberNo);
    
    boolean existsByPhone(String phone);
}

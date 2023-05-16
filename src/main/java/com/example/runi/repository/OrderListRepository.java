package com.example.runi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.runi.domain.entity.OrderListEntity;

public interface OrderListRepository extends JpaRepository<OrderListEntity, Integer>, OrderListRepositoryQDSL{

    @Query(value = "SELECT "
                    +"o.*,"
                    +"ARRAY_TO_STRING(ARRAY_AGG(p.product_name||CAST(op.p_cnt AS TEXT)||'개'),'<br>') as plist,"
                    +"sum(op.p_price) as price "
                    +"FROM orders o "
                    +"LEFT JOIN order_product op ON o.no = op.o_no "
                    +"LEFT JOIN product p ON op.p_no = p.no "
                    +"WHERE o.member_no = :memberNo "
                    +"And o.status = :status "
                    +"GROUP BY o.no "
                    +"ORDER BY o.no DESC"
    ,nativeQuery = true)
    public List<OrderListEntity> findByMemberNoAndStatusOrderByNoDesc(@Param("memberNo") Integer memberNo, @Param("status") String status);

    @Query(value = "SELECT "
                    +"o.*,"
                    +"ARRAY_TO_STRING(ARRAY_AGG(p.product_name||' : '||CAST(op.p_cnt AS TEXT)||'개'),'<br>') as plist,"
                    +"sum(op.p_price) as price "
                    +"FROM orders o "
                    +"LEFT JOIN order_product op ON o.no = op.o_no "
                    +"LEFT JOIN product p ON op.p_no = p.no "
                    +"WHERE o.phone = :phone "
                    +"GROUP BY o.no "
                    +"ORDER BY o.no DESC"
    ,nativeQuery = true)
    public List<OrderListEntity> findByPhoneNoOrderByNoDesc(@Param("phone") String phone);
    
    public boolean existsByPhone(String phone);

    @Query(value = "SELECT COALESCE(SUM(price), 0) FROM Product WHERE member_no = :memberNo ", nativeQuery = true)
    public int getMonthSales(@Param("memberNo") Integer memberNo);
}

package com.example.fogostore.repository;


import com.example.fogostore.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    @Query(value = "select * from order_detail where orderId = ?1 order by id asc", nativeQuery = true)
    List<OrderDetail> findByOrderId(Integer orderId);
}

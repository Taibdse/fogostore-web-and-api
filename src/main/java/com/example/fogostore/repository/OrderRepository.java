package com.example.fogostore.repository;

import com.example.fogostore.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = "select * from `order` where (customerFullname like %?1% OR " +
            "customerPhone like %?1% OR customerEmail like %?1%) " +
            "AND DATE(?2) <= DATE(createdAt) AND DATE(?3) >= DATE(createdAt) " +
            "AND active = true AND status = ?4",
            countQuery = "select COUNT(id) from `order` where (customerFullname like %?1% OR " +
                    "customerPhone like %?1% OR customerEmail like %?1%) " +
                    "AND DATE(?2) <= DATE(createdAt) AND DATE(?3) >= DATE(createdAt) " +
                    "AND active = true AND status = ?4",
            nativeQuery = true)
    Page<Order> findByAdmin(String customer, Date from, Date to, String status, Pageable pageable);

    @Query(value = "select * from `order` where (customerFullname like %?1% OR " +
            "customerPhone like %?1% OR customerEmail like %?1%) " +
            "AND DATE(?2) <= DATE(createdAt) AND DATE(?3) >= DATE(createdAt) " +
            "AND active = true",
            countQuery = "select COUNT(id) from `order` where (customerFullname like %?1% OR " +
                    "customerPhone like %?1% OR customerEmail like %?1%) " +
                    "AND DATE(?2) <= DATE(createdAt) AND DATE(?3) >= DATE(createdAt) " +
                    "AND active = true",
            nativeQuery = true)
    Page<Order> findByAdminWithoutStatus(String customer, Date from, Date to, Pageable pageable);
}

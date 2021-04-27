package com.example.fogostore.repository;


import com.example.fogostore.model.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;


public interface ViewRepository extends JpaRepository<View, Integer> {
    @Query(value = "select * from views where DATE(createdAt) = DATE(?1)", nativeQuery = true)
    View findByCreatedAt(Date date);

    @Query(value = "select * from views where DATE_FORMAT(createdAt, \"%m-%Y\") = ?1 order by createdAt asc", nativeQuery = true)
    List<View> findByMonthYear(String monthYear);
}

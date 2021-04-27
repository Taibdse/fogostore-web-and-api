package com.example.fogostore.repository;


import com.example.fogostore.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
    @Query(value = "select * from payment_method", nativeQuery = true)
    List<PaymentMethod> findAll();

}

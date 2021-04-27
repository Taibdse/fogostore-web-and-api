package com.example.fogostore.repository;


import com.example.fogostore.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
    @Query(value = "select * from shop order by id desc limit 1", nativeQuery = true)
    Shop findLatest();
}

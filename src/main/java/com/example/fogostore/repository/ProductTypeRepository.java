package com.example.fogostore.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.fogostore.model.ProductType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {

    @Modifying
    @Transactional
    @Query(value = "delete from product_type where productId = ?1", nativeQuery = true)
    void deleteByProductId(int productId);

    @Query(value = "select * from product_type where productId = ?1 order by sortIndex asc", nativeQuery = true)
    List<ProductType> findByProductId(int productId);
}

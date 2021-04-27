package com.example.fogostore.repository;


import com.example.fogostore.model.ProductBrandCategory;
import com.example.fogostore.model.ids.ProductBrandCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductBrandCategoryRepository extends JpaRepository<ProductBrandCategory, ProductBrandCategoryId> {

    @Modifying
    @Transactional
    @Query(value = "delete from product_brand_category where productId = ?1", nativeQuery = true)
    void deleteByProductId(int id);

    List<ProductBrandCategory> findAllByProductIdEquals(int id);
}

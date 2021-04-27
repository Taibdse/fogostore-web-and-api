package com.example.fogostore.repository;

import com.example.fogostore.model.ProductTag;
import com.example.fogostore.model.ids.ProductTagId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductTagRepository extends JpaRepository<ProductTag, ProductTagId> {
    List<ProductTag> findAllByProductIdEquals(Integer productId);
}

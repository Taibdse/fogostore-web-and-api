package com.example.fogostore.repository;

import com.example.fogostore.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    List<Tag> findAllByActiveTrue();

    @Query(value = "select * from tag where id IN " +
            "(select tagId from product_tag where productId = ?1) AND active = true", nativeQuery = true)
    List<Tag> findAllActiveByProductId(Integer productId);
}

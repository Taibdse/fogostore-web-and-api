package com.example.fogostore.repository;


import com.example.fogostore.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    @Query(value = "select * from image where productId = ?1 order by sortIndex asc", nativeQuery = true)
    List<Image> findByProductId(int id);

    @Query(value = "select * from image where type = ?1 order by sortIndex asc", nativeQuery = true)
    List<Image> findByType(String type);
}

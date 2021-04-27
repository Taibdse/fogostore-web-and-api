package com.example.fogostore.repository;


import com.example.fogostore.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query(value = "select * from brand where active = true order by sortIndex asc", nativeQuery = true)
    List<Brand> findAllActive();

    @Query(value = "select id from brand where active = true order by sortIndex asc", nativeQuery = true)
    List<Integer> findAllActiveId();

    @Query(value = "select * from brand where slug = ?1", nativeQuery = true)
    Brand findBySlug(String slug);

    @Query(value = "select * from brand where id IN ?1", nativeQuery = true)
    List<Brand> findByIds(List ids);

    @Modifying
    @Transactional
    @Query(value = "update brand set active = false where id in ?1", nativeQuery = true)
    void inactiveByIds(List<Integer> ids);
}


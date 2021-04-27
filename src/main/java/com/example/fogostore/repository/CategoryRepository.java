package com.example.fogostore.repository;


import com.example.fogostore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "select * from category where active = true order by sortIndex asc", nativeQuery = true)
    List<Category> findAllActive();

    @Query(value = "select * from category where active = true and parentId = ?1 order by sortIndex asc", nativeQuery = true)
    List<Category> findActiveChildIds(Integer parentId);

    @Query(value = "select id from category where active = true order by sortIndex asc", nativeQuery = true)
    List<Integer> findAllActiveId();

    @Query(value = "select * from category where slug = ?1", nativeQuery = true)
    Category findBySlug(String slug);

    @Query(value = "select * from category where id IN ?1", nativeQuery = true)
    List<Category> findByIds(List ids);

    @Modifying
    @Transactional
    @Query(value = "update category set active = false where id in ?1", nativeQuery = true)
    void inactiveByIds(List<Integer> ids);
}

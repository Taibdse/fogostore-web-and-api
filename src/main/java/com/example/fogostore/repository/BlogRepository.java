package com.example.fogostore.repository;


import com.example.fogostore.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    @Query(value = "select * from blog where slug = ?1", nativeQuery = true)
    Blog findBySlug(String slug);

    @Query(value = "select * from blog where active = true", nativeQuery = true)
    List<Blog> findAllActive();

    @Query(value = "select * from blog where active = true and hot = true order by sortIndex asc", nativeQuery = true)
    List<Blog> findByHot();

    @Query(value = "select * from blog where title like %?1% AND active = true",
            countQuery = "select count(id) from blog where title like %?1% AND active = true", nativeQuery = true)
    Page<Blog> findByAdmin(String blog, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update blog set sortIndex = ?2 where id = ?1", nativeQuery = true)
    void updateSortIndexById(Integer id, Integer sortIndex);

    @Modifying
    @Transactional
    @Query(value = "update blog set active = false where id IN ?1", nativeQuery = true)
    void inactivateByIds(List<Integer> ids);
}

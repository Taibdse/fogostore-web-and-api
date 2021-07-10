package com.example.fogostore.repository;

import com.example.fogostore.model.SocialPlugin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface SocialPluginRepository extends JpaRepository<SocialPlugin, Integer> {

    @Query(value = "select * from social_plugin where active = true order by sortIndex asc", nativeQuery = true)
    List<SocialPlugin> findAllActive();

    @Query(value = "select * from social_plugin where active = true AND showOnWeb = true order by sortIndex asc", nativeQuery = true)
    List<SocialPlugin> findAllShow();

    @Query(value = "select * from social_plugin where name like %?1% AND active = true",
            countQuery = "select count(id) from social_plugin where name like %?1% AND active = true", nativeQuery = true)
    Page<SocialPlugin> search(String searchValue, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update social_plugin set sortIndex = ?2 where id = ?1", nativeQuery = true)
    void updateSortIndexById(Integer id, Integer sortIndex);

    @Modifying
    @Transactional
    @Query(value = "update social_plugin set active = false where id IN ?1", nativeQuery = true)
    void inactivateByIds(List<Integer> ids);
}

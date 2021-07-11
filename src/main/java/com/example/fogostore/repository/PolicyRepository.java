package com.example.fogostore.repository;


import com.example.fogostore.dto.policy.BasicPolicy;
import com.example.fogostore.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PolicyRepository extends JpaRepository<Policy, Integer> {
    boolean existsBySlug(String slug);

    @Query(value = "select id, name, type, slug, active from policy where active = true", nativeQuery = true)
    List<BasicPolicy> findAllActive();

    Policy findBySlugEquals(String slug);

    @Query(value = "select count(id) from policy where active = true", nativeQuery = true)
    Integer countAllActive();

}

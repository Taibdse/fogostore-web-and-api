package com.example.fogostore.repository;


import com.example.fogostore.model.Website;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WebsiteRepository extends JpaRepository<Website, Integer> {
    @Query(value = "select * from website order by id desc limit 1", nativeQuery = true)
    Website findTheLatest();
}

package com.example.fogostore.repository;


import com.example.fogostore.common.enumeration.PageType;
import com.example.fogostore.model.PageMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageMetadataRepository extends JpaRepository<PageMetadata, Integer> {
    PageMetadata findByProductId(Integer productId);
    PageMetadata findByBlogId(Integer blogId);
    PageMetadata findByCategoryId(Integer categoryId);
    List<PageMetadata> findByPageType(PageType pageType);
}


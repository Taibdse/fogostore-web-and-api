package com.example.fogostore.service;

import com.example.fogostore.builder.ResultBuilder;
import com.example.fogostore.model.PageMetadata;
import com.example.fogostore.repository.PageMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface PageMetadataService {
    PageMetadata getByBlogId(Integer blogId);
    PageMetadata getByProductId(Integer productId);
    ResultBuilder save(PageMetadata pageMetadata);
}

@Service
class PageMetadataServiceImpl implements PageMetadataService{

    @Autowired
    PageMetadataRepository pageMetadataRepository;

    @Override
    public PageMetadata getByBlogId(Integer blogId) {
        return pageMetadataRepository.findByBlogId(blogId);
    }

    @Override
    public PageMetadata getByProductId(Integer productId) {
        return pageMetadataRepository.findByProductId(productId);
    }

    @Override
    public ResultBuilder save(PageMetadata pageMetadata) {
        pageMetadataRepository.save(pageMetadata);
        return ResultBuilder.build().success(true).data(pageMetadata);
    }
}

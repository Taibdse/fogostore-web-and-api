package com.example.fogostore.service;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

public interface CacheService {
    void clearProductCacheById(Integer id);
}

@Service
class CacheServiceImpl implements CacheService {

    @Override
    @CacheEvict(value = "product-by-id")
    public void clearProductCacheById(Integer id) {

    }
}

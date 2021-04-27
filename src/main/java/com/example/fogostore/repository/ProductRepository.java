package com.example.fogostore.repository;


import com.example.fogostore.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select * from product where slug = ?1", nativeQuery = true)
    Product findBySlug(String slug);

    @Query(value = "select * from product where hot = true and active = true AND available = true order by sortIndex asc", nativeQuery = true)
    List<Product> findHotProducts();

    @Query(value = "select * from product where id IN ?1 AND active = true AND available = true order by sortIndex asc", nativeQuery = true)
    List<Product> findByIdList(List<Integer> ids);

    @Query(value = "select * from product as p where active = true AND available = true AND " +
            "(select count(pt.id) from product_type as pt " +
            "where pt.productId = p.id and pt.oldPrice > 0 and pt.price < pt.oldPrice) > 0",
            countQuery = "select count(p.id) from product as p where active = true AND available = true AND " +
                    "(select count(pt.id) from product_type as pt " +
                    "where pt.productId = p.id and pt.oldPrice > 0 and pt.price < pt.oldPrice) > 0",
            nativeQuery = true)
    Page<Product> findDiscountProducts(Pageable pageable);

    boolean existsBySlugEquals(String slug);

    boolean existsByIdEquals(int id);

    @Modifying
    @Transactional
    @Query(value = "update product set active = false where id = ?1", nativeQuery = true)
    void inactivateById(int id);

    @Modifying
    @Transactional
    @Query(value = "update product set active = false where id IN ?1", nativeQuery = true)
    void inactivateByIds(List<Integer> ids);

    @Query(value = "select * from product where name like %?1% AND active = true AND available = true order by id DESC",
            countQuery = "select count(id) from product where name like %?1% AND active = true AND available = true",
            nativeQuery = true)
    Page<Product> findBySearchValue(String searchValue, Pageable pageable);

    @Query(value = "select * from product where active = true AND available = true",
            countQuery = "select COUNT(id) from product where active = true AND available = true",
            nativeQuery = true)
    Page<Product> findAllActive(Pageable pageable);

    @Query(value = "select * from product where active = true AND available = true",
            nativeQuery = true)
    List<Product> findAllActiveWithoutPagination();

    @Query(value = "select count(productId) from product_brand_category where categoryId = ?1", nativeQuery = true)
    Integer countByCategoryId(Integer categoryId);

    @Query(value = "select count(productId) from product_brand_category where brandId = ?1", nativeQuery = true)
    Integer countByBrandId(Integer brandId);

    @Query(value = "select count(p.id) from product as p where active = true AND available = true AND " +
            "(select count(pt.id) from product_type as pt " +
            "where pt.productId = p.id and pt.oldPrice > 0 and pt.price < pt.oldPrice) > 0", nativeQuery = true)
    Integer countDiscount();

    @Query(value = "select * from product where active = true AND available = true AND name like %?2% " +
            "AND id IN (select productId from product_brand_category where categoryId IN ?1)",
            countQuery = "select COUNT(*) from product where active = true AND available = true " +
                    "AND id IN (select productId from product_brand_category where categoryId IN ?1)",
            nativeQuery = true)
    Page<Product> findByCategoryIdList(List<Integer> categoryIds, String searchValue, Pageable pageable);

    @Query(value = "select * from product where active = true AND available = true AND name like %?2% " +
            "AND id IN (select productId from product_brand_category where brandId IN ?1)",
            countQuery = "select COUNT(*) from product where active = true AND available = true " +
                    "AND id IN (select productId from product_brand_category where brandId IN ?1)",
            nativeQuery = true)
    Page<Product> findByBrandIdList(List<Integer> brandIds, String searchValue, Pageable pageable);


    @Query(value = "select * from product p where active = true AND available = true " +
            "AND name like %?1% AND id IN " +
            "(select distinct(A.productId) from (" +
            "(select * from product_brand_category where categoryId IN ?2) as A " +
            "INNER JOIN (select * from product_brand_category where brandId IN ?3) as B " +
            "ON A.productId = B.productId))",
            countQuery = "select count(id) from product p where active = true AND available = true " +
                    "AND name like %?1% AND id IN " +
                    "(select distinct(A.productId) from (" +
                    "(select * from product_brand_category where categoryId IN ?2) as A " +
                    "INNER JOIN (select * from product_brand_category where brandId IN ?3) as B " +
                    "ON A.productId = B.productId))",
            nativeQuery = true)
    Page<Product> findByEndUser(String search, List<Integer> categoryIds, List<Integer> brandIds, Pageable pageable);
}

package com.example.fogostore.common.constants;

public class JpaQuery {
    public static final String QUERY_PRODUCT_BASIC_FIELDS = "select id, name, slug, image, price, active, available, hot, isNew, sortIndex, relatedProductIds from product";
}

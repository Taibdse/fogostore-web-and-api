package com.example.fogostore.common.constants;

public class CustomJpaQuery {
    public static final String COUNT_DISCOUNT_PRODUCTS = "select count(p.id) from product as p where active = true AND " +
            "((p.newPrice < p.oldPrice) OR (select count(pt.id) from product_type as pt " +
            "where pt.productId = p.id and pt.oldPrice > 0 and pt.price < pt.oldPrice) > 0)";

    public static final String QUERY_DISCOUNT_PRODUCTS = "select * from product as p where active = true AND " +
            "((p.newPrice < p.oldPrice) OR (select count(pt.id) from product_type as pt " +
            "where pt.productId = p.id and pt.oldPrice > 0 and pt.price < pt.oldPrice) > 0)";

}

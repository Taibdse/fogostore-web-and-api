package com.example.fogostore.common.constants;

import java.util.ArrayList;
import java.util.List;

public class ProductSortBy {
    public static final String DEFAULT = "default";
    public static final String CREATED_AT_ASC = "created_at_asc";
    public static final String CREATED_AT_DESC = "created_at_desc";
    public static final String SORT_INDEX_ASC = "sort_index_asc";
    public static final String SORT_INDEX_DESC = "sort_index_desc";
    public static final String PRICE_ASC = "price_asc";
    public static final String PRICE_DESC = "price_desc";

    public static List<String> getList(){
        List<String> list = new ArrayList<>();
        list.add(DEFAULT);
        list.add(CREATED_AT_ASC);
        list.add(CREATED_AT_DESC);
        list.add(PRICE_ASC);
        list.add(PRICE_DESC);
        return list;
    }
}

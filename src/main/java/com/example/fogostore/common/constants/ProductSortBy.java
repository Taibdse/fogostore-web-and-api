package com.example.fogostore.common.constants;

import java.util.ArrayList;
import java.util.List;

public class ProductSortBy {
    public static final String DEFAULT = "mac_dinh";
    public static final String CREATED_AT_ASC = "cu_nhat";
    public static final String CREATED_AT_DESC = "moi_nhat";
    public static final String PRICE_ASC = "gia_tang";
    public static final String PRICE_DESC = "gia_giam";

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

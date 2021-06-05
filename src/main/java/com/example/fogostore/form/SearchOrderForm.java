package com.example.fogostore.form;

import com.example.fogostore.common.constants.OrderSortBy;
import com.example.fogostore.common.constants.OrderStatus;
import com.example.fogostore.common.constants.PageSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchOrderForm {
    private String customer = "";
    private Date fromDate;
    private Date toDate;
    private String status = OrderStatus.ALL;
    private Integer page = 1;
    private Integer size = PageSize.PAGE_SIZE_10;
    private String sortBy = OrderSortBy.CREATED_AT_DESC;
}
package com.example.fogostore.form;

import com.example.fogostore.common.constants.PageSize;
import com.example.fogostore.common.constants.ProductSortBy;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchProductForm {
    private String searchValue = "";
    private Integer page = 1;
    private Integer size = PageSize.PAGE_SIZE_10;
    private Integer brandId = 0;
    private Integer categoryId = 0;
    private String sortBy = ProductSortBy.SORT_INDEX_ASC;
}
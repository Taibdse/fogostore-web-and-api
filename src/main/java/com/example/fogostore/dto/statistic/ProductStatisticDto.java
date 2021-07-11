package com.example.fogostore.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductStatisticDto {
    private Integer total;
    private Integer hotTotal;
    private Integer inStockTotal;
    private Integer outOfStockTotal;
    private Integer discountTotal;
}

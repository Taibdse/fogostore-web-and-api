package com.example.fogostore.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatisticDto {
    private Integer total;
    private Integer doneTotal;
    private Integer newTotal;
    private Integer contactingTotal;
    private Integer deliveringTotal;
    private Integer canceledTotal;
}

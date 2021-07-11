package com.example.fogostore.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticDto {
   OrderStatisticDto orderStatistic;
   ProductStatisticDto productStatistic;
   CategoryStatisticDto categoryStatistic;
   BrandStatisticDto brandStatistic;
   PolicyStatisticDto policyStatistic;
   BlogStatisticDto blogStatistic;
   TagStatisticDto tagStatistic;
}

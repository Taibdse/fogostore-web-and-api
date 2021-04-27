package com.example.fmanracing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeDto implements Serializable {
    private Integer id;
    private Integer productId;
    private String name;
    private Double price;
    private Double oldPrice;
    private int sortIndex;
}

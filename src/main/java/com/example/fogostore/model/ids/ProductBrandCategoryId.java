package com.example.fogostore.model.ids;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductBrandCategoryId implements Serializable {
    private Integer productId;
    private Integer brandId;
    private Integer categoryId;
}

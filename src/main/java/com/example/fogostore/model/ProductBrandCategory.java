package com.example.fogostore.model;

import com.example.fogostore.model.ids.ProductBrandCategoryId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "product_brand_category")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ProductBrandCategoryId.class)
public class ProductBrandCategory {
    @Id
    private Integer productId;

    @Id
    private Integer brandId;

    @Id
    private Integer categoryId;

    @Override
    public boolean equals(Object obj) {
        if (this== obj) return true;
        if (obj ==null|| getClass() != obj.getClass()) return false;
        ProductBrandCategory that = (ProductBrandCategory) obj;
        return that.getBrandId() == brandId
                && that.getCategoryId() == categoryId
                && that.getProductId() == productId;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}


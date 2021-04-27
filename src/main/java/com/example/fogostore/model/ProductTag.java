package com.example.fogostore.model;

import com.example.fogostore.model.ids.ProductTagId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "product_tag")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ProductTagId.class)
public class ProductTag {
    @Id
    private Integer productId;

    @Id
    private Integer tagId;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ProductTag that = (ProductTag) obj;
        return that.getTagId() == tagId
                && that.getProductId() == productId;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}


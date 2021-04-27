package com.example.fogostore.model;

import com.example.fogostore.common.utils.CustomStringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product_type")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer productId;
    private String name;
    private Double price;
    private Double oldPrice;
    private int sortIndex;

    @JsonIgnore
    public String getDisplayPrice(){
        return CustomStringUtils.getPriceFormatted(price);
    }

    @JsonIgnore
    public String getDisplayOldPrice(){
        return CustomStringUtils.getPriceFormatted(oldPrice);
    }
}

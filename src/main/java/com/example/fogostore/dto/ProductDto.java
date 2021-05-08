package com.example.fogostore.dto;

import com.example.fogostore.common.utils.CustomStringUtils;
import com.example.fogostore.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {
    private Integer id;
    private String name;
    private String description;
    private String slug;
    private double oldPrice;
    private double newPrice;
    private boolean active;
    private boolean available;
    private boolean hot;
    private boolean isNew;
    private Integer sortIndex;
    private String techInfo;

    private List<Category> categories;
    private List<Brand> brands;
    private List<Tag> tags;

    private List<String> subImages;
    private String mainImage;
    private List<Integer> categoryIds;
    private List<Integer> brandIds;
    private List<Integer> tagIds;
    private List<ProductType> productTypes;
    private PageMetadata pageMetadata;

    @JsonIgnore
    public Double getPrice(){
        if(productTypes.size() > 0){
            return productTypes.get(0).getPrice();
        }
        return newPrice;
    }

    @JsonIgnore
    public Double getOlderPrice(){
        if(productTypes.size() > 0) return productTypes.get(0).getOldPrice();
        return oldPrice;
    }

    @JsonIgnore
    public ProductType getType(){
        if(productTypes.size() > 0){
            return productTypes.get(0);
        }
        return null;
    }
}

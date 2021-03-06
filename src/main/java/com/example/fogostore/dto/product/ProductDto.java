package com.example.fogostore.dto.product;

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
    private String relatedProductIds;

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
    private List<ProductDto> relatedProducts;

    public Double getPrice() {
        if (productTypes != null && productTypes.size() > 0) {
            return productTypes.get(0).getPrice();
        }
        return newPrice;
    }

    @JsonIgnore
    public ProductType getType() {
        if (productTypes.size() > 0) {
            return productTypes.get(0);
        }
        return null;
    }
}

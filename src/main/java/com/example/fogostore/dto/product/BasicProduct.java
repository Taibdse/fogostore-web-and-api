package com.example.fogostore.dto.product;

public interface BasicProduct {
    Integer getId();
    String getName();
    String getSlug();
    String getImage();
    String getPrice();
    Boolean getActive();
    Boolean getAvailable();
    Boolean getHot();
    Boolean getIsNew();
    Integer getSortIndex();
    String getRelatedProductIds();
}

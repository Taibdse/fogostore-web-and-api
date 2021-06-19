package com.example.fogostore.common.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInclusionFieldsBuilder {
    private Boolean includeProductTypeList = true;
    private Boolean includePageMetadata = false;
    private Boolean includeBrandAndCategoryList = false;
    private Boolean includeTagList = false;
    private Boolean includeSubImages = false;
    private Boolean includeRelatedProducts = false;

    public static ProductInclusionFieldsBuilder build() {
        return new ProductInclusionFieldsBuilder();
    }

    public ProductInclusionFieldsBuilder includeAll() {
        this.setIncludePageMetadata(true);
        this.setIncludeBrandAndCategoryList(true);
        this.setIncludeProductTypeList(true);
        this.setIncludeTagList(true);
        this.setIncludeSubImages(true);
        this.setIncludeRelatedProducts(true);
        return this;
    }

    public ProductInclusionFieldsBuilder includeBrandAndCategoryList(Boolean value) {
        this.setIncludeBrandAndCategoryList(value);
        return this;
    }
}

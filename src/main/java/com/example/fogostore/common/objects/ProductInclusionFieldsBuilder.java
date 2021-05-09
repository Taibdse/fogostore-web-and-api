package com.example.fogostore.common.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInclusionFieldsBuilder {
    private Boolean includePageMetadata = false;
    private Boolean includeBrandAndCategoryList = false;
    private Boolean includeProductTypeList = true;
    private Boolean includeTagList = false;
    private Boolean includeSubImages = false;

    public static ProductInclusionFieldsBuilder build() {
        return new ProductInclusionFieldsBuilder();
    }

    public ProductInclusionFieldsBuilder includeAll() {
        this.setIncludePageMetadata(true);
        this.setIncludeBrandAndCategoryList(true);
        this.setIncludeProductTypeList(true);
        this.setIncludeTagList(true);
        this.setIncludeSubImages(true);
        return this;
    }
}

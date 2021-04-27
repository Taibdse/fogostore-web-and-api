package com.example.fogostore.dto;

import com.example.fogostore.common.enumeration.PageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageMetadataDto implements Serializable {
    private Integer id;
    private String title;
    private String keywords;
    private String description;
    private PageType pageType;
    private Integer productId;
    private Integer blogId;
    private Integer categoryId;

    private String image;
    private Double productPrice;
}

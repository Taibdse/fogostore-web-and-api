package com.example.fogostore.dto;

import com.example.fogostore.common.utils.CustomStringUtils;
import com.example.fogostore.model.Brand;
import com.example.fogostore.model.Category;
import com.example.fogostore.model.PageMetadata;
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
public class CategoryDto implements Serializable {
    private Integer id;
    private Integer parentId;
    private String name;
    private String image;
    private String slug;
    private List<Integer> productIds;
    private Integer sortIndex;
    private PageMetadata pageMetadata;
}

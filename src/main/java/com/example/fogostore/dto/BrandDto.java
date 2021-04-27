package com.example.fogostore.dto;

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
public class BrandDto implements Serializable {
    private Integer id;
    private Integer parentId;
    private String name;
    private String slug;
    private Integer sortIndex;
    private List<Integer> productIds;
}

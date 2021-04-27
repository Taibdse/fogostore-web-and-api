package com.example.fogostore.dto;

import com.example.fogostore.model.PageMetadata;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogDto implements Serializable {
    private Integer id;

    private String title;
    private String slug;
    private String shortDescription;
    private String content;
    private String type;
    private String image;
    private Date createdAt;
    private Date updatedAt;
    private boolean active;
    private PageMetadata pageMetadata;
    private Integer sortIndex;
}

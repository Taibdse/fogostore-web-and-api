package com.example.fogostore.model;

import com.example.fogostore.common.enumeration.PageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "page_metadata")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String keywords;
    private String description;
    private PageType pageType;
    private Integer productId;
    private Integer blogId;
    private Integer categoryId;
}

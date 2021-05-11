package com.example.fogostore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}

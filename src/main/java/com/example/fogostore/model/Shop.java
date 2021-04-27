package com.example.fogostore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "shop")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String phone;
    private String shopName;
    private String facebookLink;
    private String instagramLink;
    private String shopAddress;
    private String addressLink;
    private String aboutUsContent;
    private String logo;
    private String websiteLink;
    private String zaloLink;
    private String youtubeLink;
}

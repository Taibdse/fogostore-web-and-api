package com.example.fogostore.dto;

import com.example.fogostore.model.PageMetadata;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopDto implements Serializable {
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
    private PageMetadata pageMetadata;
}

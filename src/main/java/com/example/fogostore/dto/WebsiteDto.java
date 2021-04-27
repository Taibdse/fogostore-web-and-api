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
public class WebsiteDto implements Serializable {
    private Integer id;
    private String information;
    private List<String> homePageImages;
    private String subPageImage;
    private String logoWebsiteImage;
    private List<String> customerPartnerImages;
}

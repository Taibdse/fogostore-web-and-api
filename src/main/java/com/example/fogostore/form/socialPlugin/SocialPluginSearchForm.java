package com.example.fogostore.form.socialPlugin;

import com.example.fogostore.common.enumeration.SocialPluginPosition;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialPluginSearchForm {
    private Integer page;
    private Integer size;
    private String searchValue;
}
package com.example.fogostore.form.socialPlugin;

import com.example.fogostore.common.enumeration.SocialPluginPosition;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialPluginForm {
    private Integer id;
    private String name;
    private String code;
    private SocialPluginPosition position;
    private Boolean active;
    private Boolean showOnWeb;
    private Integer sortIndex;
}
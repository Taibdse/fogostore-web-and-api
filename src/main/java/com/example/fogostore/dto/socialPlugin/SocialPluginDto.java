package com.example.fogostore.dto.socialPlugin;

import com.example.fogostore.common.enumeration.SocialPluginPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SocialPluginDto implements Serializable {
    private Integer id;
    private String name;
    private SocialPluginPosition position;
    private String code;
    private Boolean active;
    private Boolean showOnWeb;
    private Integer sortIndex;
}

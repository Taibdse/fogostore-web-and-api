package com.example.fogostore.model;

import com.example.fogostore.common.enumeration.SocialPluginPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "social_plugin")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SocialPlugin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String code;
    @Enumerated(EnumType.STRING)
    private SocialPluginPosition position;
    private Boolean active;
    private Boolean showOnWeb;
    private Integer sortIndex;
}

package com.example.fogostore.dto;

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
public class CustomerContactDto implements Serializable {
    private Integer id;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private String customerEmail;
    private String contactContent;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private boolean active;
}

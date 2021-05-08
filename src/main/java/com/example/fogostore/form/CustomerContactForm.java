package com.example.fogostore.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerContactForm {
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

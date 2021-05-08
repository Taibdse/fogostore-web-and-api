package com.example.fogostore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "customer_contact")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate(){
        updatedAt = new Date();
    }
}

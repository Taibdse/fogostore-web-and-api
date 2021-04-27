package com.example.fogostore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_detail")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int orderId;
    private int productId;
    private double productPrice;
    private int quantity;
    private String productName;
    private String productImage;
    private String productInfo;
    private Integer productTypeId;
    private String productTypeName;
}

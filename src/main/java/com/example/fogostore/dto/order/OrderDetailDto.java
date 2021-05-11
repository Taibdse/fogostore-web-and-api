package com.example.fogostore.dto.order;

import com.example.fogostore.common.utils.CustomStringUtils;
import com.example.fogostore.model.ProductType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto implements Serializable {
    private Integer id;

    private int orderId;
    private int productId;
    private double productPrice;
    private int quantity;
    private String productName;
    private String productImage;
    private Integer productTypeId;
    private String productTypeName;
    private String productInfo;
    private double totalPrice;

    @JsonIgnore
    public String getFormattedPrice(double price){
        return CustomStringUtils.getPriceFormatted(price);
    }



}

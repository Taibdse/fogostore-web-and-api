package com.example.fogostore.dto;

import com.example.fogostore.common.constants.OrderStatus;
import com.example.fogostore.common.utils.CustomStringUtils;
import com.example.fogostore.common.utils.DateTimeUtils;
import com.example.fogostore.model.OrderDetail;
import com.example.fogostore.model.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto implements Serializable {
    private Integer id;
    private String customerPhone;
    private String customerEmail;
    private String customerAddress;
    private String customerFullname;
    private double shippingMoney;
    private String status;
    private String customerNote;
    private String shopNote;
    private boolean active;
    private Date createdAt;
    private Date updatedAt;
    private Integer paymentMethodId;

    private List<OrderDetailDto> orderDetails;
    private PaymentMethod paymentMethod;

    private double totalPrice;
    private double totalProductPrice;

    @JsonIgnore
    public String getFormattedPrice(double price){
        return CustomStringUtils.getPriceFormatted(price);
    }

    @JsonIgnore
    public String getDisplayStatus(){
        if(StringUtils.isEmpty(status)) return "";
        switch (status){
            case OrderStatus.NEW:
                return "Chờ xác nhận";
            case OrderStatus.CONTACTING:
                return "Đang liên hệ";
            case OrderStatus.DELIVERING:
                return "Đang giao";
            case OrderStatus.DONE:
                return "Giao hàng thành công";
            case OrderStatus.CANCEL:
                return "Đơn hàng đã bị hủy";
            default:
                return "không có";
        }
    }

    @JsonIgnore
    public String getTime(){
        return DateTimeUtils.getLocalTime(createdAt);
    }

}

package com.chotot.doantotnghiep.dto;

import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private double totalPrice;
    private int status;
    private String locate;
    private String notes;
    private double paid;
    private double needPay;
    private ProductEntity product;
    private UserEntity buyer;
    private String paidFormat;
    private String needPayFormat;
    private String totalPriceFormat;
    private String payPriceFormat;
}

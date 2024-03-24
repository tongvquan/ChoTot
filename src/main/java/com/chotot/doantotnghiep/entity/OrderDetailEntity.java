package com.chotot.doantotnghiep.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "orderdetail")
public class OrderDetailEntity extends BaseEntity{

    @Column(name = "paid")
    private double paid;//da thanh toan

    @Column(name = "needpay")
    private double needPay;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}

package com.chotot.doantotnghiep.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity{

    @Column(name = "totalprice")
    private double totalPrice;

    @Column(name = "status")
    private int status;


    @OneToMany(mappedBy = "order")
    private List<OrderDetailEntity> orderDetailEntities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;


}

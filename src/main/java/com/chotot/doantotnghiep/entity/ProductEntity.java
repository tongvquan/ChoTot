package com.chotot.doantotnghiep.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "product")
public class ProductEntity extends BaseEntity{

    @Column(name = "name")
    private String name;
//
//    @Column(name = "title")
//    private String title;

    @Column(name = "price")
    private double price;

    @Column(name = "rate")
    private int rate;

    @Column(name = "description")
    private String description;

    @Column(name = "locate")
    private String locate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "image")
    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @OneToMany(mappedBy = "product")
    private List<OrderDetailEntity> orderDetailEntities;

}

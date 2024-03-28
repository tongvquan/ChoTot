package com.chotot.doantotnghiep.dto;

import com.chotot.doantotnghiep.entity.CategoryEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ProductDto {
    private Long id;
    private String name;
    private int rate;
    private String description;
    private CategoryEntity category;
    private double price;
    private String locate;
    private String image;
    private Date createdDate;
    private Date modifiedDate;
    private String createdBy;
    private String modifiedBy;
    private String posted;
    private String priceFormat;
    private UserEntity userId;
}

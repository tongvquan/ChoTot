package com.chotot.doantotnghiep.service.impl;

import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.entity.UserEntity;

import java.util.List;

public interface IProductService {
    List<ProductEntity> findAllByUser();
    Boolean create(ProductEntity product);

    ProductEntity findById(Long id);

    Boolean update(ProductEntity product);

    Boolean delete(Long id);

    List<ProductEntity> findAllOrderByModifiedDateAsc();
}

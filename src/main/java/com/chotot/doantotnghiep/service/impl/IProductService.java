package com.chotot.doantotnghiep.service.impl;

import com.chotot.doantotnghiep.dto.CategoryDto;
import com.chotot.doantotnghiep.dto.ProductDto;
import com.chotot.doantotnghiep.entity.CategoryEntity;
import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    List<ProductDto> findAllByUser();
    Boolean create(ProductDto product);

    ProductDto findById(Long id);

    Boolean update(ProductDto product);

    Boolean delete(Long id);

    List<ProductDto> findFirst8ByOrderByModifiedDateDesc();
    List<ProductDto> findRandom8Products();

    List<ProductDto> findByCategory(CategoryDto category);
    Page<ProductDto> findAll(Integer pageNo);
    Page<ProductDto> findAllByOrderByModifiedDateDesc(Integer pageNo);
}

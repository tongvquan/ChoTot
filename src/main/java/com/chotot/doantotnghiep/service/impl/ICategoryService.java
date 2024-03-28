package com.chotot.doantotnghiep.service.impl;

import com.chotot.doantotnghiep.dto.CategoryDto;
import com.chotot.doantotnghiep.entity.CategoryEntity;

import java.util.List;

public interface ICategoryService {
    List<CategoryDto> getAll();

    CategoryDto findByName(String name);
}

package com.chotot.doantotnghiep.service.impl;

import com.chotot.doantotnghiep.dto.CategoryDto;
import com.chotot.doantotnghiep.entity.CategoryEntity;

import java.util.List;

public interface ICategoryService {
    List<CategoryDto> getAll();

    Boolean save(CategoryDto categoryDto);

    CategoryDto findByName(String name);
}

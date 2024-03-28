package com.chotot.doantotnghiep.mapper;

import com.chotot.doantotnghiep.dto.CategoryDto;
import com.chotot.doantotnghiep.dto.ProductDto;
import com.chotot.doantotnghiep.entity.CategoryEntity;
import com.chotot.doantotnghiep.entity.ProductEntity;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class CategoryMapper {
    public static CategoryDto toDTO(CategoryEntity entity) {
        CategoryDto dto = new CategoryDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
    public static CategoryEntity toEntity(CategoryDto dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }
}

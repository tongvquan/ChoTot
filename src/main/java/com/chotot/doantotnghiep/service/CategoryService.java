package com.chotot.doantotnghiep.service;

import com.chotot.doantotnghiep.dto.CategoryDto;
import com.chotot.doantotnghiep.entity.CategoryEntity;
import com.chotot.doantotnghiep.mapper.CategoryMapper;
import com.chotot.doantotnghiep.repository.CategoryRepository;
import com.chotot.doantotnghiep.service.impl.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<CategoryDto> getAll() {
        List<CategoryDto> list = new ArrayList<>();
        for(CategoryEntity category : categoryRepository.findAll()){
            CategoryDto dto = new CategoryDto();
            dto.setId(category.getId());
            dto.setName(category.getName());
            list.add(dto);
        }
        return list;
    }

    @Override
    public CategoryDto findByName(String name) {
        CategoryDto categoryDto = CategoryMapper.toDTO(categoryRepository.findByName(name));
        return categoryDto;
    }
}

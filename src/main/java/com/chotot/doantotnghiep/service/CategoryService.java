package com.chotot.doantotnghiep.service;

import com.chotot.doantotnghiep.entity.CategoryEntity;
import com.chotot.doantotnghiep.repository.CategoryRepository;
import com.chotot.doantotnghiep.service.impl.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<CategoryEntity> getAll() {
        return categoryRepository.findAll();
    }
}

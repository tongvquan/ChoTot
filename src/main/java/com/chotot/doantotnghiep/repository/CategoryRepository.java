package com.chotot.doantotnghiep.repository;

import com.chotot.doantotnghiep.entity.CategoryEntity;
import com.chotot.doantotnghiep.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findAll();
}

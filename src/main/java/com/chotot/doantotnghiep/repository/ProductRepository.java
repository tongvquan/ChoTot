package com.chotot.doantotnghiep.repository;

import com.chotot.doantotnghiep.dto.ProductDto;
import com.chotot.doantotnghiep.entity.CategoryEntity;
import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByUser(UserEntity userEntity);
    Optional<ProductEntity> findById(Long id);
    List<ProductEntity> findFirst8ByOrderByModifiedDateDesc();
    @Query(value = "SELECT * FROM product ORDER BY RAND() LIMIT 8", nativeQuery = true)
    List<ProductEntity> findRandom8Products();
    List<ProductEntity> findByCategory(CategoryEntity category);
    Page<ProductEntity> findAll(Pageable page);
    Page<ProductEntity> findAllByOrderByModifiedDateDesc(Pageable page);


}

package com.chotot.doantotnghiep.repository;

import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByUser(UserEntity userEntity);
    Optional<ProductEntity> findById(Long id);
}

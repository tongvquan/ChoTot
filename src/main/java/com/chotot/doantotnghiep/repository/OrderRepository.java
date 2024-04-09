package com.chotot.doantotnghiep.repository;

import com.chotot.doantotnghiep.dto.OrderDto;
import com.chotot.doantotnghiep.dto.ProductDto;
import com.chotot.doantotnghiep.entity.CategoryEntity;
import com.chotot.doantotnghiep.entity.OrderEntity;
import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByProduct(ProductEntity product);

    List<OrderEntity> findAllByBuyer(UserEntity user);

    Optional<OrderEntity> findById(Long id);

//    OrderEntity findByProduct(ProductEntity product);
}

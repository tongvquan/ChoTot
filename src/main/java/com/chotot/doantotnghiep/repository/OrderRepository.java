package com.chotot.doantotnghiep.repository;

import com.chotot.doantotnghiep.entity.OrderEntity;
import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findByProduct(ProductEntity product);

    List<OrderEntity> findAllByBuyer(UserEntity user);

    Optional<OrderEntity> findById(Long id);

    @Query(value = "select sum(o.totalprice) from orders o where month(o.modifieddate) = ?1", nativeQuery = true)
    Double monthlyProfit(int month);

//    OrderEntity findByProduct(ProductEntity product);
}

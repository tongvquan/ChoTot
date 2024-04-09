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
    List<ProductEntity> findAllBySeller(UserEntity userEntity);

    Optional<ProductEntity> findById(Long id);
    List<ProductEntity> findFirst8ByStatusOrderByModifiedDateDesc(int status);
    @Query(value = "SELECT * FROM product where status = 0 ORDER BY RAND() LIMIT 8", nativeQuery = true)
    List<ProductEntity> findRandom8Products();
    List<ProductEntity> findByCategoryAndStatus(CategoryEntity category, int status);
    Page<ProductEntity> findAllByStatus(Pageable page, int status);
    Page<ProductEntity> findAllByStatusOrderByModifiedDateDesc(Pageable page, int status);

    Page<ProductEntity> findByStatusAndNameLike(int status ,Pageable page, String keyword);
    List<ProductEntity> findByStatus(int status);

    Long countByStatus(int status);
}


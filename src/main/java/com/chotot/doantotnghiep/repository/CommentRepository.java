package com.chotot.doantotnghiep.repository;

import com.chotot.doantotnghiep.entity.CommentEntity;
import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByProductAndCmtIdIsNull(ProductEntity product);

    @Query(value = "SELECT * FROM comment as reply where reply.cmtid = :id", nativeQuery = true)
    List<CommentEntity> findByCmtId(@Param("id") Long id);
}

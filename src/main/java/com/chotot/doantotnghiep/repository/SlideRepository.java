package com.chotot.doantotnghiep.repository;

import com.chotot.doantotnghiep.entity.SlideEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SlideRepository extends JpaRepository<SlideEntity, Long> {
    Optional<SlideEntity> findById(Long id);

    List<SlideEntity> findAll();
}

package com.chotot.doantotnghiep.repository;

import com.chotot.doantotnghiep.entity.RoleEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    List<RoleEntity> findByName(String name);
}

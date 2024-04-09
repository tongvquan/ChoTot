package com.chotot.doantotnghiep.repository;

import com.chotot.doantotnghiep.entity.UserEntity;
import com.chotot.doantotnghiep.utils.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserName(String userName);

    Optional<UserEntity> findById(Long id);
    Long countByStatus(Boolean status);
}

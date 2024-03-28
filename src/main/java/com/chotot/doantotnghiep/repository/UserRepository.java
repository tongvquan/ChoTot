package com.chotot.doantotnghiep.repository;

import com.chotot.doantotnghiep.entity.UserEntity;
import com.chotot.doantotnghiep.utils.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserName(String userName);

    List<UserEntity> findAllByAction(Action action);
}

package com.chotot.doantotnghiep.service.impl;

import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.entity.UserEntity;

public interface IUserService {
    UserEntity findByUserName(String userName);

    Boolean create(UserEntity userEntity);
}
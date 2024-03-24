package com.chotot.doantotnghiep.service;

import com.chotot.doantotnghiep.entity.UserEntity;
import com.chotot.doantotnghiep.repository.RoleRepository;
import com.chotot.doantotnghiep.repository.UserRepository;
import com.chotot.doantotnghiep.service.impl.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserEntity findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Boolean create(UserEntity userEntity) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        UserEntity user = userRepository.findByUserName(userEntity.getUserName());
        userEntity.setRoles(roleRepository.findByName("USER"));
        if(user == null){
            userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
            userRepository.save(userEntity);
            return true;
        }
        else {
            return false;
        }
    }
}

package com.chotot.doantotnghiep.service;

import com.chotot.doantotnghiep.dto.UserDto;
import com.chotot.doantotnghiep.entity.UserEntity;
import com.chotot.doantotnghiep.repository.RoleRepository;
import com.chotot.doantotnghiep.repository.UserRepository;
import com.chotot.doantotnghiep.service.impl.IUserService;
import com.chotot.doantotnghiep.utils.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Boolean create(UserDto dto) {
        if(userRepository.findByUserName(dto.getUserName()) != null){
            return false;
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(dto.getUserName());
        userEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
        userEntity.setRoles(roleRepository.findByName("USER"));
        userEntity.setPhoneNumber(dto.getPhoneNumber());
        userEntity.setAddress(dto.getAddress());
        userEntity.setFullName(dto.getFullName());
        userRepository.save(userEntity);
        return true;
    }

    @Override
    public void saveUser(UserEntity user) {
        user.setAction(Action.ONLINE);
        userRepository.save(user);
    }

    @Override
    public void disconnect(UserEntity user) {
        var storedUser = userRepository.findById(user.getId())
                .orElse(null);
        if(storedUser != null){
            storedUser.setAction(Action.OFFLINE);
            userRepository.save(storedUser);
        }

    }

    @Override
    public List<UserEntity> findConnectesUsers() {
        return userRepository.findAllByAction(Action.ONLINE);
    }
}

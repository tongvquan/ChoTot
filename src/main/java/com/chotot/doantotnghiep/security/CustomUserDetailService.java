package com.chotot.doantotnghiep.security;

import com.chotot.doantotnghiep.entity.RoleEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import com.chotot.doantotnghiep.repository.UserRepository;
import com.chotot.doantotnghiep.service.UserService;
import com.chotot.doantotnghiep.service.impl.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private IUserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.findByUserName(username);
        if(userEntity == null){
            throw new UsernameNotFoundException("not found");
        }
        Collection<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        for (RoleEntity role : userEntity.getRoles()){
            grantedAuthoritySet.add(new SimpleGrantedAuthority(role.getName()));
        }
        MyUser myUser = new MyUser(userEntity.getUserName(), userEntity.getPassword(), true, true, true, true, grantedAuthoritySet);
        myUser.setId(userEntity.getId());
        myUser.setFullName(userEntity.getFullName());
        return myUser;
    }
}

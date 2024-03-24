package com.chotot.doantotnghiep.service;

import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import com.chotot.doantotnghiep.repository.ProductRepository;
import com.chotot.doantotnghiep.service.impl.IProductService;
import com.chotot.doantotnghiep.service.impl.IStorageService;
import com.chotot.doantotnghiep.service.impl.IUserService;
import com.chotot.doantotnghiep.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private IUserService userService;

    @Override
    public List<ProductEntity> findAllByUser() {
        UserEntity userEntity = userService.findByUserName(SecurityUtils.getCurrentUserName());
        return productRepository.findAllByUser(userEntity);
    }

    @Override
    public Boolean create(ProductEntity product) {
        try {
            UserEntity userEntity = userService.findByUserName(SecurityUtils.getCurrentUserName());
            product.setUser(userEntity);
            productRepository.save(product);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ProductEntity findById(Long id) {
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        return productEntity.get();
    }

    @Override
    public Boolean update(ProductEntity product) {
        try {
            UserEntity userEntity = userService.findByUserName(SecurityUtils.getCurrentUserName());
            product.setUser(userEntity);
            productRepository.save(product);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean delete(Long id) {
        try {
            this.productRepository.delete(findById(id));
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<ProductEntity> findAllOrderByModifiedDateAsc() {
        return productRepository.findFirst8ByOrderByModifiedDateAsc();
    }

}

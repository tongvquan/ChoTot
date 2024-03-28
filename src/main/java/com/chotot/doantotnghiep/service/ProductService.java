package com.chotot.doantotnghiep.service;

import com.chotot.doantotnghiep.dto.CategoryDto;
import com.chotot.doantotnghiep.dto.ProductDto;
import com.chotot.doantotnghiep.entity.CategoryEntity;
import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import com.chotot.doantotnghiep.mapper.CategoryMapper;
import com.chotot.doantotnghiep.mapper.ProductMapper;
import com.chotot.doantotnghiep.repository.ProductRepository;
import com.chotot.doantotnghiep.service.impl.IProductService;
import com.chotot.doantotnghiep.service.impl.IStorageService;
import com.chotot.doantotnghiep.service.impl.IUserService;
import com.chotot.doantotnghiep.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.NumberFormat;
import java.util.*;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private IUserService userService;

    @Override
    public List<ProductDto> findAllByUser() {
        UserEntity userEntity = userService.findByUserName(SecurityUtils.getCurrentUserName());
        List<ProductDto> list = new ArrayList<>();
        for(ProductEntity entity : productRepository.findAllByUser(userEntity)){
            ProductDto dto = ProductMapper.toDTO(entity);
            list.add(dto);
        }
        return list;
    }

    @Override
    public Boolean create(ProductDto dto) {

        try {
            ProductEntity product = ProductMapper.toEntity(dto);
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
    public ProductDto findById(Long id) {
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        return ProductMapper.toDTO(productEntity.get());
    }

    @Override
    public Boolean update(ProductDto dto) {
        Optional<ProductEntity> product = productRepository.findById(dto.getId());
        if(product.isPresent()){
            ProductEntity existingProductEntity = product.get();
            try {
                existingProductEntity.setName(dto.getName());
                existingProductEntity.setRate(dto.getRate());
                existingProductEntity.setDescription(dto.getDescription());
                existingProductEntity.setCategory(dto.getCategory());
                existingProductEntity.setPrice(dto.getPrice());
                existingProductEntity.setLocate(dto.getLocate());
                existingProductEntity.setImage(dto.getImage());
                existingProductEntity.setUser(userService.findByUserName(SecurityUtils.getCurrentUserName()));
                productRepository.save(existingProductEntity);
                return true;
            }
            catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        else {
            return false;
        }
    }

    @Override
    public Boolean delete(Long id) {
        try {
            ProductEntity entity = ProductMapper.toEntity(findById(id));
            this.productRepository.delete(entity);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<ProductDto> findFirst8ByOrderByModifiedDateDesc() {
        List<ProductDto> list = new ArrayList<>();
        for(ProductEntity entity : productRepository.findFirst8ByOrderByModifiedDateDesc()){
            ProductDto dto = ProductMapper.toDTO(entity);
            list.add(dto);
        }
        return list;
    }


    @Override
    public List<ProductDto> findRandom8Products() {
        List<ProductDto> list = new ArrayList<>();
        for(ProductEntity entity : productRepository.findRandom8Products()){
            ProductDto dto = ProductMapper.toDTO(entity);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<ProductDto> findByCategory(CategoryDto dto) {
        List<ProductDto> list = new ArrayList<>();
        CategoryEntity categoryEntity = CategoryMapper.toEntity(dto);
        for(ProductEntity entity : productRepository.findByCategory(categoryEntity)){
            ProductDto productDto = ProductMapper.toDTO(entity);
            list.add(productDto);
        }
        return list;
    }

    @Override
    public Page<ProductDto> findAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 10);
        Page<ProductEntity> productPage = productRepository.findAll(pageable);

        List<ProductDto> dtos = new ArrayList<>();
        for (ProductEntity entity : productPage.getContent()) {
            ProductDto productDto = ProductMapper.toDTO(entity);
            dtos.add(productDto);
        }

        return new PageImpl<>(dtos, pageable, productPage.getTotalElements());
    }

    @Override
    public Page<ProductDto> findAllByOrderByModifiedDateDesc(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 10);
        Page<ProductEntity> productPage = productRepository.findAllByOrderByModifiedDateDesc(pageable);

        List<ProductDto> dtos = new ArrayList<>();
        for (ProductEntity entity : productPage.getContent()) {
            ProductDto productDto = ProductMapper.toDTO(entity);
            dtos.add(productDto);
        }

        return new PageImpl<>(dtos, pageable, productPage.getTotalElements());
    }

    @Override
    public Page<ProductDto> searchProduct(Integer pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo - 1, 5);
        Page<ProductEntity> productPage = productRepository.findByNameLike(pageable,"%" + keyword + "%");
        List<ProductDto> list = new ArrayList<>();
        for(ProductEntity entity : productPage.getContent()){
            ProductDto productDto = ProductMapper.toDTO(entity);
            list.add(productDto);
        }
        return new PageImpl<>(list, pageable, productPage.getTotalElements());
    }

}

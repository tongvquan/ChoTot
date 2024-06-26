package com.chotot.doantotnghiep.service;

import com.chotot.doantotnghiep.dto.CategoryDto;
import com.chotot.doantotnghiep.dto.ProductDto;
import com.chotot.doantotnghiep.entity.CategoryEntity;
import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import com.chotot.doantotnghiep.mapper.CategoryMapper;
import com.chotot.doantotnghiep.mapper.ProductMapper;
import com.chotot.doantotnghiep.repository.OrderRepository;
import com.chotot.doantotnghiep.repository.ProductRepository;
import com.chotot.doantotnghiep.service.impl.IOrderService;
import com.chotot.doantotnghiep.service.impl.IProductService;
import com.chotot.doantotnghiep.service.impl.IUserService;
import com.chotot.doantotnghiep.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private IUserService userService;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<ProductDto> findAllByUser() {
        UserEntity userEntity = userService.findByUserName(SecurityUtils.getCurrentUserName());
        List<ProductDto> list = new ArrayList<>();
        for(ProductEntity entity : productRepository.findAllBySellerOrderByStatusAsc(userEntity)){
            ProductDto dto = ProductMapper.toDTO(entity);
            dto.setStatus(entity.getStatus());
            list.add(dto);
        }
        return list;
    }

    @Override
    public Boolean create(ProductDto dto) {

        try {
            ProductEntity product = ProductMapper.toEntity(dto);
            UserEntity userEntity = userService.findByUserName(SecurityUtils.getCurrentUserName());
            product.setSeller(userEntity);
            productRepository.save(product);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ProductDto findById(Long id) {
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        return ProductMapper.toDTO(productEntity.get());
    }

    @Override
    @Transactional
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
                existingProductEntity.setSeller(userService.findByUserName(SecurityUtils.getCurrentUserName()));
                existingProductEntity.setStatus(dto.getStatus());
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
        for(ProductEntity entity : productRepository.findFirst8ByStatusOrderByModifiedDateDesc(0)){
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
        for(ProductEntity entity : productRepository.findByCategoryAndStatus(categoryEntity, 0)){
            ProductDto productDto = ProductMapper.toDTO(entity);
            list.add(productDto);
        }
        return list;
    }

    @Override
    public Page<ProductDto> findAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 10);
        Page<ProductEntity> productPage = productRepository.findAllByStatus(pageable, 0);

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
        Page<ProductEntity> productPage = productRepository.findAllByStatusOrderByModifiedDateDesc(pageable, 0);

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
        Page<ProductEntity> productPage = productRepository.findByStatusAndNameLike(0,pageable,"%" + keyword + "%");
        List<ProductDto> list = new ArrayList<>();
        for(ProductEntity entity : productPage.getContent()){
            ProductDto productDto = ProductMapper.toDTO(entity);
            list.add(productDto);
        }
        return new PageImpl<>(list, pageable, productPage.getTotalElements());
    }

    @Override
    public Boolean updateStt(Long id) {
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        ProductEntity existingProductEntity = productEntity.get();
        try {
            existingProductEntity.setStatus(existingProductEntity.getStatus()+1);
            productRepository.save(existingProductEntity);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Long countByStatus(int a) {
        return productRepository.countByStatus(a);
    }

}

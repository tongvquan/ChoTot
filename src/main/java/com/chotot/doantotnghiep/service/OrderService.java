package com.chotot.doantotnghiep.service;

import com.chotot.doantotnghiep.dto.OrderDto;
import com.chotot.doantotnghiep.dto.ProductDto;
import com.chotot.doantotnghiep.entity.CategoryEntity;
import com.chotot.doantotnghiep.entity.OrderEntity;
import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import com.chotot.doantotnghiep.mapper.CategoryMapper;
import com.chotot.doantotnghiep.mapper.OrderMapper;
import com.chotot.doantotnghiep.mapper.ProductMapper;
import com.chotot.doantotnghiep.repository.OrderRepository;
import com.chotot.doantotnghiep.repository.ProductRepository;
import com.chotot.doantotnghiep.repository.UserRepository;
import com.chotot.doantotnghiep.service.impl.IOrderService;
import com.chotot.doantotnghiep.service.impl.IProductService;
import com.chotot.doantotnghiep.service.impl.IUserService;
import com.chotot.doantotnghiep.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private IUserService userService;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private IProductService productService;

    @Override
    @Transactional
    public OrderDto create(OrderDto dto, Long id) {

            OrderEntity order = OrderMapper.toEntity(dto);

            UserEntity userEntity = userService.findByUserName(SecurityUtils.getCurrentUserName());
            order.setBuyer(userEntity);

            Optional<ProductEntity> productEntity = productRepository.findById(id);
            ProductEntity existingProductEntity = productEntity.get();
            try {
                existingProductEntity.setStatus(1);
                productRepository.save(existingProductEntity);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            order.setProduct(productEntity.get());
            order.setTotalPrice(productEntity.get().getPrice());
            order.setNeedPay(order.getTotalPrice()-order.getPaid());
            orderRepository.save(order);
            return OrderMapper.toDTO(order);
    }

    @Override
    public List<OrderDto> confirm() {
        List<OrderDto> list = new ArrayList<>();
        for(ProductEntity productEntity :  productRepository.findByStatus(1)){
            OrderEntity order = orderRepository.findByProduct(productEntity);
            OrderDto orderDto = OrderMapper.toDTO(order);
            list.add(orderDto);
        }
        return list;
    }

    @Override
    public List<OrderDto> success() {
        List<OrderDto> list = new ArrayList<>();
        for(ProductEntity productEntity :  productRepository.findByStatus(5)){
            OrderDto orderDto = OrderMapper.toDTO(orderRepository.findByProduct(productEntity));
            list.add(orderDto);
        }
        return list;
    }

    @Override
    public List<OrderDto> pay() {
        List<OrderDto> list = new ArrayList<>();
        for(ProductEntity productEntity :  productRepository.findByStatus(6)){
            OrderDto orderDto = OrderMapper.toDTO(orderRepository.findByProduct(productEntity));
            list.add(orderDto);
        }
        return list;
    }

    @Override
    public List<OrderDto> history() {
        List<OrderDto> list = new ArrayList<>();
        for(ProductEntity productEntity :  productRepository.findByStatus(7)){
            OrderDto orderDto = OrderMapper.toDTO(orderRepository.findByProduct(productEntity));
            list.add(orderDto);
        }
        return list;
    }

    @Override
    public List<OrderDto> findAllByBuyer() {
        UserEntity userEntity = userService.findByUserName(SecurityUtils.getCurrentUserName());
        List<OrderDto> list = new ArrayList<>();
        for(OrderEntity entity : orderRepository.findAllByBuyer(userEntity)){
            OrderDto dto = OrderMapper.toDTO(entity);
            list.add(dto);
        }
        return list;
    }

    @Override
    public OrderDto findById(Long id) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        return OrderMapper.toDTO(orderEntity.get());
    }

    @Override
    public Boolean delete(Long id) {
        try {
            OrderEntity entity = orderRepository.findById(id).get();
            this.orderRepository.delete(entity);


            Optional<ProductEntity> productEntity = productRepository.findById(entity.getProduct().getId());
            ProductEntity existingProductEntity = productEntity.get();
            try {
                existingProductEntity.setStatus(0);
                productRepository.save(existingProductEntity);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean updateStt(Long id) {
        try {
            OrderEntity entity = orderRepository.findById(id).get();


            Optional<ProductEntity> productEntity = productRepository.findById(entity.getProduct().getId());
            ProductEntity existingProductEntity = productEntity.get();
            try {
                existingProductEntity.setStatus(existingProductEntity.getStatus()+1);
                productRepository.save(existingProductEntity);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public double monthlyProfit(int month) {
        if(orderRepository.monthlyProfit(month) == null)
            return 0;
        else
            return orderRepository.monthlyProfit(month)*0.1;
    }


    @Override
    public OrderDto findByProduct(ProductDto productDto) {
        ProductEntity product = productRepository.findById(productDto.getId()).get();
        return OrderMapper.toDTO(orderRepository.findByProduct(product));
    }


}

package com.chotot.doantotnghiep.service.impl;

import com.chotot.doantotnghiep.dto.OrderDto;
import com.chotot.doantotnghiep.dto.ProductDto;
import com.chotot.doantotnghiep.dto.UserDto;
import com.chotot.doantotnghiep.entity.OrderEntity;
import com.chotot.doantotnghiep.entity.ProductEntity;

import java.util.List;

public interface IOrderService {
    OrderDto create(OrderDto orderDto, Long id);

    List<OrderDto> confirm();

    List<OrderDto> success();
    List<OrderDto> pay();

    List<OrderDto> findAllByBuyer();

    OrderDto findById(Long id);
    Boolean delete(Long id);

//    OrderDto findByProduct(ProductDto productDto);

}

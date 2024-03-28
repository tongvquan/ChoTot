package com.chotot.doantotnghiep.mapper;

import com.chotot.doantotnghiep.dto.OrderDto;
import com.chotot.doantotnghiep.dto.ProductDto;
import com.chotot.doantotnghiep.entity.OrderEntity;
import com.chotot.doantotnghiep.entity.ProductEntity;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class OrderMapper {
    public static OrderDto toDTO(OrderEntity entity) {
        Locale vietnamLocale = new Locale("vi", "VN");
        NumberFormat vietnamFormat = NumberFormat.getCurrencyInstance(vietnamLocale);
        OrderDto dto = new OrderDto();
        dto.setId(entity.getId());
        dto.setTotalPrice(entity.getTotalPrice());
        dto.setLocate(entity.getLocate());
        dto.setNotes(entity.getNotes());
        dto.setPaid(entity.getPaid());
        dto.setNeedPay(entity.getNeedPay());
        dto.setUser(entity.getBuyer());
        dto.setProduct(entity.getProduct());

        dto.setPaidFormat(vietnamFormat.format(dto.getPaid()));
        dto.setNeedPayFormat(vietnamFormat.format(dto.getNeedPay()));
        dto.setTotalPriceFormat(vietnamFormat.format(dto.getTotalPrice()));
        return dto;
    }
    public static OrderEntity toEntity(OrderDto dto) {
        OrderEntity entity = new OrderEntity();
        entity.setTotalPrice(dto.getTotalPrice());
        entity.setLocate(dto.getLocate());
        entity.setNotes(dto.getNotes());
        entity.setPaid(dto.getPaid());
        return entity;
    }
}

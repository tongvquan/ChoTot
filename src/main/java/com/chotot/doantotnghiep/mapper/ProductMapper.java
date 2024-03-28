package com.chotot.doantotnghiep.mapper;

import com.chotot.doantotnghiep.dto.ProductDto;
import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import com.chotot.doantotnghiep.utils.SecurityUtils;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class ProductMapper {
    public static ProductDto toDTO(ProductEntity entity) {
        ProductDto dto = new ProductDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setRate(entity.getRate());
        dto.setCategory(entity.getCategory());
        dto.setLocate(entity.getLocate());
        dto.setImage(entity.getImage());
        dto.setPrice(entity.getPrice());
        dto.setDescription(entity.getDescription());
        dto.setModifiedBy(entity.getModifiedBy());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUserId(entity.getUser());
        //lay ra thoi gian da dang bai viet ban hang
        Date date = new Date();
        double posted=0;
        if(dto.getModifiedDate() == null){
            posted =(date.getTime() - dto.getCreatedDate().getTime());
            if(posted<3600000){
                int a = (int) (posted/60000);
                dto.setPosted(String.valueOf(a)+" phút trước");
            }
            else {
                int a = (int) (posted/3600000);
                dto.setPosted(String.valueOf(a)+" giờ trước");
            }
        }
        else {
            posted = (date.getTime()-dto.getModifiedDate().getTime());
            if(posted<3600000){
                int a = (int) (posted/60000);
                dto.setPosted(String.valueOf(a)+" phút trước");
            }
            else {
                int a = (int) (posted/3600000);
                dto.setPosted(String.valueOf(a)+" giờ trước");
            }
        }

        //dinh dang tien viet
        Locale vietnamLocale = new Locale("vi", "VN");
        NumberFormat vietnamFormat = NumberFormat.getCurrencyInstance(vietnamLocale);
        dto.setPriceFormat(vietnamFormat.format(dto.getPrice()));
        return dto;
    }
    public static ProductEntity toEntity(ProductDto dto) {
        ProductEntity entity = new ProductEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setRate(dto.getRate());
        entity.setDescription(dto.getDescription());
        entity.setCategory(dto.getCategory());
        entity.setPrice(dto.getPrice());
        entity.setLocate(dto.getLocate());
        entity.setImage(dto.getImage());
        return entity;
    }
}

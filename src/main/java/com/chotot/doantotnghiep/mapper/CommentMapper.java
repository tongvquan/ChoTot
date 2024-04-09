package com.chotot.doantotnghiep.mapper;

import com.chotot.doantotnghiep.dto.CategoryDto;
import com.chotot.doantotnghiep.dto.CommentDto;
import com.chotot.doantotnghiep.entity.CategoryEntity;
import com.chotot.doantotnghiep.entity.CommentEntity;

import java.util.Date;

public class CommentMapper {
    public static CommentDto toDTO(CommentEntity entity) {
        CommentDto dto = new CommentDto();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setUser(entity.getUser());
        dto.setCreateAt(entity.getCreateAt());
        dto.setCmtId(entity.getCmtId());
        Date date = new Date();
        double posted=0;
        posted =(date.getTime() - dto.getCreateAt().getTime());
        if(posted<3600000){
            int a = (int) (posted/60000);
            dto.setPosted(String.valueOf(a)+" phút trước");
        }
        else {
            int a = (int) (posted/3600000);
            dto.setPosted(String.valueOf(a)+" giờ trước");
        }
        return dto;
    }
    public static CommentEntity toEntity(CommentDto dto) {
        CommentEntity entity = new CommentEntity();
        entity.setId(dto.getId());
        entity.setContent(dto.getContent());
        entity.setCmtId(dto.getCmtId());
        return entity;
    }
}

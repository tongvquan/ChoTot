package com.chotot.doantotnghiep.dto;

import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private String content;
    private Date createAt;
    private UserEntity user;
    private String posted;
    private Long productId;
    private Long cmtId;
    private List<CommentDto> replies;
}

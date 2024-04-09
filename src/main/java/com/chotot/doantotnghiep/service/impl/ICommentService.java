package com.chotot.doantotnghiep.service.impl;

import com.chotot.doantotnghiep.dto.CommentDto;
import com.chotot.doantotnghiep.entity.CommentEntity;

import java.util.List;

public interface ICommentService {
    CommentEntity insert(CommentDto commentDto, Long id);
    void delete(Long id);
    List<CommentDto> getCommentByProduct(Long productId);
}

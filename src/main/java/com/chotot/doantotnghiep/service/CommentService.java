package com.chotot.doantotnghiep.service;

import com.chotot.doantotnghiep.dto.CommentDto;
import com.chotot.doantotnghiep.entity.CommentEntity;
import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import com.chotot.doantotnghiep.mapper.CommentMapper;
import com.chotot.doantotnghiep.repository.CommentRepository;
import com.chotot.doantotnghiep.repository.ProductRepository;
import com.chotot.doantotnghiep.service.impl.ICommentService;
import com.chotot.doantotnghiep.service.impl.IUserService;
import com.chotot.doantotnghiep.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService implements ICommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public CommentEntity insert(CommentDto commentDto, Long id) {
        CommentEntity comment = CommentMapper.toEntity(commentDto);
        UserEntity userEntity = userService.findByUserName(SecurityUtils.getCurrentUserName());
        comment.setUser(userEntity);
        ProductEntity product = productRepository.findById(id).get();
        comment.setProduct(product);
        return commentRepository.save(comment);
    }



    @Override
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<CommentDto> getCommentByProduct(Long productId) {
        ProductEntity product =  productRepository.findById(productId).get();
        List<CommentEntity> list = commentRepository.findByProductAndCmtIdIsNull(product);
        List<CommentDto> listDto = new ArrayList<>();

        for(CommentEntity entity : list){
            CommentDto dto = CommentMapper.toDTO(entity);
            List<CommentDto> replies = new ArrayList<>();
            for(CommentEntity rep : commentRepository.findByCmtId(entity.getId())){
                CommentDto reply = CommentMapper.toDTO(rep);
                replies.add(reply);
                dto.setReplies(replies);
            }
            listDto.add(dto);
        }
        return listDto;
    }




}

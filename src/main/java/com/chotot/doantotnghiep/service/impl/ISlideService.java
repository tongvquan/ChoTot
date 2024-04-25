package com.chotot.doantotnghiep.service.impl;

import com.chotot.doantotnghiep.dto.ProductDto;
import com.chotot.doantotnghiep.entity.SlideEntity;

import java.util.List;

public interface ISlideService {
    Boolean save(SlideEntity slide);
    Boolean update(SlideEntity slide);

    List<SlideEntity> findAll();
    Boolean delete(Long id);

//    Boolean updateStt(SlideEntity slide);
}

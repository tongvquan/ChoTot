package com.chotot.doantotnghiep.service.impl;

import com.chotot.doantotnghiep.dto.ProductDto;
import com.chotot.doantotnghiep.entity.SlideEntity;

public interface ISlideService {
    Boolean save(SlideEntity slide);
    Boolean update(SlideEntity slide);

//    Boolean updateStt(SlideEntity slide);
}

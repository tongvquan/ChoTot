package com.chotot.doantotnghiep.service;

import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.entity.SlideEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import com.chotot.doantotnghiep.mapper.ProductMapper;
import com.chotot.doantotnghiep.repository.SlideRepository;
import com.chotot.doantotnghiep.service.impl.ISlideService;
import com.chotot.doantotnghiep.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SlideService implements ISlideService {
    @Autowired
    private SlideRepository slideRepository;
    @Override
    public Boolean save(SlideEntity slide) {
        try {
            slideRepository.save(slide);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean update(SlideEntity slide) {
        Optional<SlideEntity> slideEntity = slideRepository.findById(slide.getId());
        if(slideEntity.isPresent()){
            SlideEntity existingSlideEntity = slideEntity.get();
            try {
                existingSlideEntity.setName(slide.getName());
                return true;
            }
            catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        else {
            return false;
        }
    }

    @Override
    public List<SlideEntity> findAll() {
        return slideRepository.findAll();
    }

    @Override
    public Boolean delete(Long id) {
        try {
            this.slideRepository.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}

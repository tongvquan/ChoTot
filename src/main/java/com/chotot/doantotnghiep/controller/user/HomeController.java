package com.chotot.doantotnghiep.controller.user;

import com.chotot.doantotnghiep.dto.CategoryDto;
import com.chotot.doantotnghiep.dto.ProductDto;
import com.chotot.doantotnghiep.entity.CategoryEntity;
import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.entity.SlideEntity;
import com.chotot.doantotnghiep.service.impl.ICategoryService;
import com.chotot.doantotnghiep.service.impl.IProductService;
import com.chotot.doantotnghiep.service.impl.ISlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @Autowired
    private ISlideService slideService;

    @GetMapping("/")
    public String home(Model model){
        List<CategoryDto> category = categoryService.getAll();
        model.addAttribute("categories", category);
        List<ProductDto> newProduct = productService.findFirst8ByOrderByModifiedDateDesc();
        model.addAttribute("newProduct", newProduct);
        List<ProductDto> allProduct = productService.findRandom8Products();
        model.addAttribute("allProduct", allProduct);
        model.addAttribute("pageTitle", "Trang chủ");

        List<SlideEntity> slideEntities = slideService.findAll();
        model.addAttribute("slides", slideEntities);
        return "index";
    }

}

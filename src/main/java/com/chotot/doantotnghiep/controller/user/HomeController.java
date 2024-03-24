package com.chotot.doantotnghiep.controller.user;

import com.chotot.doantotnghiep.entity.CategoryEntity;
import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.service.impl.ICategoryService;
import com.chotot.doantotnghiep.service.impl.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @GetMapping("/")
    public String home(Model model){
        List<CategoryEntity> category = categoryService.getAll();
        model.addAttribute("categories", category);
        List<ProductEntity> productEntities = productService.findAllOrderByModifiedDateAsc();
        model.addAttribute("products", productEntities);
        return "index";
    }
}

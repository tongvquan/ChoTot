package com.chotot.doantotnghiep.controller.user;

import com.chotot.doantotnghiep.dto.CategoryDto;
import com.chotot.doantotnghiep.dto.ProductDto;
import com.chotot.doantotnghiep.entity.CategoryEntity;
import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.service.impl.ICategoryService;
import com.chotot.doantotnghiep.service.impl.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping("/")
    public String home(Model model){
        List<CategoryDto> category = categoryService.getAll();
        model.addAttribute("categories", category);
        List<ProductDto> newProduct = productService.findFirst8ByOrderByModifiedDateDesc();
        model.addAttribute("newProduct", newProduct);
        List<ProductDto> allProduct = productService.findRandom8Products();
        model.addAttribute("allProduct", allProduct);
        return "index";
    }

    @GetMapping("/{categoryName}")
    public String getProductByCategory(Model model,@PathVariable("categoryName")String name){
        List<ProductDto> product = productService.findByCategory(categoryService.findByName(name));
        model.addAttribute("products", product);
        return "getbycategory";
    }
    @GetMapping("/new-product")
    public String newProduct(Model model, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo){
        Page<ProductDto> list = productService.findAllByOrderByModifiedDateDesc(pageNo);
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("listNewProduct", list);
        return "orderbymodify";
    }
    @GetMapping("/all-product")
    public String allProduct(Model model, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo){
        Page<ProductDto> list = productService.findAllByOrderByModifiedDateDesc(pageNo);
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("listAllProduct", list);
        return "allproduct";
    }

}

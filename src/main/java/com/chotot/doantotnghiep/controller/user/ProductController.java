package com.chotot.doantotnghiep.controller.user;

import com.chotot.doantotnghiep.dto.CategoryDto;
import com.chotot.doantotnghiep.dto.ProductDto;
import com.chotot.doantotnghiep.entity.CategoryEntity;
import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import com.chotot.doantotnghiep.security.MyUser;
import com.chotot.doantotnghiep.service.impl.ICategoryService;
import com.chotot.doantotnghiep.service.impl.IProductService;
import com.chotot.doantotnghiep.service.impl.IStorageService;
import com.chotot.doantotnghiep.service.impl.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private IProductService productService;

    @Autowired
    private IStorageService storageService;

    @Autowired
    private ICategoryService categoryService;

    @RequestMapping("/sell-product")
    public String sell(Model model){
        ProductEntity product = new ProductEntity();
        model.addAttribute("product",product);
        List<CategoryDto> category = categoryService.getAll();
        model.addAttribute("categories", category);
        return "sell";
    }
    @PostMapping("/sell-product")
    public String save(@ModelAttribute("product") ProductDto product, @RequestParam("inputImage")MultipartFile file){
        this.storageService.store(file);
        String fileName = file.getOriginalFilename();
        product.setImage(fileName);
        if(productService.create(product)){
            return "redirect:/manage-product";
        }
        else {
            return "redirect:/sell";
        }
    }

    @RequestMapping("/manage-product")
    public String home(Model model){
        List<ProductDto> list = productService.findAllByUser();
        model.addAttribute("listProduct",list);
        return "manage";
    }

    @GetMapping("/edit-product/{id}")
    public String edit(Model model, @PathVariable("id") Long id){
        ProductDto product = this.productService.findById(id);
        model.addAttribute("product",product);
        List<CategoryDto> category = categoryService.getAll();
        model.addAttribute("categories", category);
        return "edit";
    }
    @PostMapping("/edit-product")
    public String edits(@ModelAttribute("product") ProductDto product,
                        @RequestParam("inputImage") MultipartFile file,
                        @RequestParam("currentImage") String currentImage) {
        if (file.isEmpty()) {
            product.setImage(currentImage);
        } else {
            String fileName = file.getOriginalFilename();
            this.storageService.store(file);
            product.setImage(fileName);
        }

        if (productService.update(product)) {
            return "redirect:/manage-product";
        } else {
            return "redirect:/edit-product/" + product.getId();
        }
    }


    @GetMapping("/delete-product/{id}")
    public String delete(@PathVariable("id")Long id){
        if(productService.delete(id)){
            return "redirect:/";
        }
        else {
            return "redirect:/manage-product";
        }
    }
}

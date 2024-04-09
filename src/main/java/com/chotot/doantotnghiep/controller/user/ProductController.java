package com.chotot.doantotnghiep.controller.user;

import com.chotot.doantotnghiep.dto.CategoryDto;
import com.chotot.doantotnghiep.dto.ProductDto;
import com.chotot.doantotnghiep.entity.CategoryEntity;
import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import com.chotot.doantotnghiep.security.MyUser;
import com.chotot.doantotnghiep.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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

//    @Autowired
//    private IOrderService orderService;

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

    @PostMapping("/confirm/{id}")
    public String updateStt(@PathVariable("id") Long id) {
        if (productService.updateStt(id)) {
            return "redirect:/manage-product";
        } else {
            return "redirect:/";
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
        Page<ProductDto> list = productService.findAll(pageNo);
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("listAllProduct", list);
        return "allproduct";
    }

    @GetMapping("/search/product")
    public String searchProduct(Model model, @Param("keyword") String keyword, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo){
        Page<ProductDto> list = productService.searchProduct(pageNo,keyword);
        model.addAttribute("list", list);
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("keyword", keyword);
        return "search";
    }
}

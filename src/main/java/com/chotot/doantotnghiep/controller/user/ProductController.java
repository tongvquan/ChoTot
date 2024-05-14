package com.chotot.doantotnghiep.controller.user;

import com.chotot.doantotnghiep.dto.CategoryDto;
import com.chotot.doantotnghiep.dto.OrderDto;
import com.chotot.doantotnghiep.dto.ProductDto;
import com.chotot.doantotnghiep.entity.CategoryEntity;
import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import com.chotot.doantotnghiep.security.MyUser;
import com.chotot.doantotnghiep.service.impl.*;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class ProductController {
    @Autowired
    private IProductService productService;

    @Autowired
    private IStorageService storageService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private Cloudinary cloudinary;

    @RequestMapping("/sell-product")
    public String sell(Model model){
        ProductEntity product = new ProductEntity();
        product.setRate(null);
        product.setPrice(null);
        model.addAttribute("product",product);
        List<CategoryDto> category = categoryService.getAll();
        model.addAttribute("categories", category);
        model.addAttribute("pageTitle", "Đăng bán");
        return "sell";
    }

    @PostMapping("/sell-product")
    public String save(@ModelAttribute("product") ProductDto product, @RequestParam("inputImage")MultipartFile file) throws IOException {
//        this.storageService.store(file);
//        String fileName = file.getOriginalFilename();
        String img =  cloudinary.uploader()
                .upload(file.getBytes(),
                        Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();
        product.setImage(img);
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
        model.addAttribute("pageTitle", "Quản lý");
        return "manage";
    }

    @GetMapping("/edit-product/{id}")
    public String edit(Model model, @PathVariable("id") Long id){
        ProductDto product = this.productService.findById(id);
        model.addAttribute("product",product);
        List<CategoryDto> category = categoryService.getAll();
        model.addAttribute("categories", category);
        model.addAttribute("pageTitle", "Sửa");
        return "edit";
    }
    @PostMapping("/edit-product")
    public String edits(@ModelAttribute("product") ProductDto product,
                        @RequestParam("inputImage") MultipartFile file,
                        @RequestParam("currentImage") String currentImage) throws IOException {
        if (file.isEmpty()) {
            product.setImage(currentImage);
        } else {
            String img =  cloudinary.uploader()
                    .upload(file.getBytes(),
                            Map.of("public_id", UUID.randomUUID().toString()))
                    .get("url")
                    .toString();
            product.setImage(img);
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
        model.addAttribute("pageTitle", "Danh mục");
        return "getbycategory";
    }
    @GetMapping("/new-product")
    public String newProduct(Model model, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo){
        Page<ProductDto> list = productService.findAllByOrderByModifiedDateDesc(pageNo);
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("listNewProduct", list);
        model.addAttribute("pageTitle", "Mới");
        return "orderbymodify";
    }
    @GetMapping("/all-product")
    public String allProduct(Model model, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo){
        Page<ProductDto> list = productService.findAll(pageNo);
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("listAllProduct", list);
        model.addAttribute("pageTitle", "Danh sách");
        return "allproduct";
    }

    @GetMapping("/search/product")
    public String searchProduct(Model model, @Param("keyword") String keyword, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo){
        Page<ProductDto> list = productService.searchProduct(pageNo,keyword);
        model.addAttribute("list", list);
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageTitle", "Tìm kiếm");
        return "search";
    }
}

package com.chotot.doantotnghiep.controller.user;

import com.chotot.doantotnghiep.dto.ProductDto;
import com.chotot.doantotnghiep.entity.UserEntity;
import com.chotot.doantotnghiep.security.MyUser;
import com.chotot.doantotnghiep.service.impl.IProductService;
import com.chotot.doantotnghiep.service.impl.IUserService;
import com.chotot.doantotnghiep.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class DetailController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IUserService userService;

    @GetMapping("/product/detail/{id}")
    public String getProductByCategory(Model model, @PathVariable("id")Long id){
        ProductDto product = productService.findById(id);
        model.addAttribute("productdetail", product);
        return "detail";
    }
}

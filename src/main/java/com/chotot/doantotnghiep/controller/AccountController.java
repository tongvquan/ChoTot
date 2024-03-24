package com.chotot.doantotnghiep.controller;

import com.chotot.doantotnghiep.entity.ProductEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import com.chotot.doantotnghiep.service.UserService;
import com.chotot.doantotnghiep.service.impl.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AccountController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/register")
    public String register(Model model){
        UserEntity userEntity = new UserEntity();
        model.addAttribute("user",userEntity);
        return "register";
    }
    @PostMapping("/register")
    public String save(@ModelAttribute("user") UserEntity user){
        if (userService.create(user)){
            return "redirect:/login";
        }
        else {
            return "redirect:/register";
        }
    }

}

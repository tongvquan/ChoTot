package com.chotot.doantotnghiep.controller;

import com.chotot.doantotnghiep.dto.UserDto;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public String save(@ModelAttribute("user") UserDto user){
        String phoneNumber = user.getPhoneNumber();
        System.out.println(">>> check phoneNumber" + phoneNumber);
        if (!isValidPhoneNumber(phoneNumber)) {
            System.out.println(">>> check 1");
            return "redirect:/register?error=invalidPhoneNumber";
        }
        System.out.println(">>> check 2");

        if (userService.create(user)){
            System.out.println(">>> check 3");
            return "redirect:/login";
        }
        else {
            System.out.println(">>> check 4");
            return "redirect:/register";
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "(?:0|\\+84)?([0-9]{9})";

        // Tạo pattern từ regex
        Pattern pattern = Pattern.compile(phoneRegex);
        // Tạo matcher từ số điện thoại và pattern
        Matcher matcher = pattern.matcher(phoneNumber);
        // Kiểm tra xem matcher có khớp với regex không
        return matcher.matches();
    }

}

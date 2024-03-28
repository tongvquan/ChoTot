package com.chotot.doantotnghiep.controller.admin;

import com.chotot.doantotnghiep.dto.OrderDto;
import com.chotot.doantotnghiep.dto.ProductDto;
import com.chotot.doantotnghiep.service.impl.IOrderService;
import com.chotot.doantotnghiep.service.impl.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private IProductService productService;

    @RequestMapping("/home")
    public String home(Model model){
        List<OrderDto> confirm = orderService.confirm();
        model.addAttribute("confirm",confirm);
        for(OrderDto orderDto : confirm){
            String phone =  orderDto.getProduct().getSeller().getPhoneNumber();
            model.addAttribute("phone", phone);
        }
        return "admin/index";
    }

    @PostMapping("/confirm/{id}")
    public String updateStt(@PathVariable("id") Long id) {
        if (productService.updateStt(id)) {
            return "redirect:/admin/home";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping("/success")
    public String success(Model model){
        List<OrderDto> success = orderService.success();
        model.addAttribute("success",success);
        for(OrderDto orderDto : success){
            String phone =  orderDto.getProduct().getSeller().getPhoneNumber();
            model.addAttribute("phone", phone);
        }
        return "admin/success";
    }

    @RequestMapping("/pay")
    public String pay(Model model){
        List<OrderDto> pay = orderService.pay();
        model.addAttribute("pay",pay);
        for(OrderDto orderDto : pay){
            String phone =  orderDto.getProduct().getSeller().getPhoneNumber();
            model.addAttribute("phone", phone);
        }
        return "admin/pay";
    }
}
package com.chotot.doantotnghiep.controller.user;

import com.chotot.doantotnghiep.dto.OrderDto;
import com.chotot.doantotnghiep.dto.ProductDto;
import com.chotot.doantotnghiep.entity.OrderEntity;
import com.chotot.doantotnghiep.entity.UserEntity;
import com.chotot.doantotnghiep.service.impl.IOrderService;
import com.chotot.doantotnghiep.service.impl.IProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IProductService productService;

    @GetMapping("/product/order/{id}")
    public String orderProduct(Model model, @PathVariable("id")Long id){
        ProductDto product = productService.findById(id);
        model.addAttribute("orderProduct", product);
        OrderEntity orderEntity = new OrderEntity();
        model.addAttribute("order",orderEntity);
        return "cart";
    }

    @PostMapping("/product/order")
    public String order(@ModelAttribute("order") OrderDto orderDto,
                        @RequestParam("productId") Long id,
                        RedirectAttributes redirectAttributes){
        if (orderService.create(orderDto, id) != null){
            redirectAttributes.addAttribute("orderId", orderService.create(orderDto, id).getId());
            return "redirect:/payment";
        }
        else {
            return "redirect:/cart";
        }
    }

    @RequestMapping("/payment")
    public String payment(Model model, @RequestParam("orderId") Long id){
        OrderDto orderDto = orderService.findById(id);
        model.addAttribute("order",orderDto);
        return "pay";
    }
}
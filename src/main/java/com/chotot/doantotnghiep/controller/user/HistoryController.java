package com.chotot.doantotnghiep.controller.user;

import com.chotot.doantotnghiep.dto.CategoryDto;
import com.chotot.doantotnghiep.dto.OrderDto;
import com.chotot.doantotnghiep.dto.ProductDto;
import com.chotot.doantotnghiep.entity.OrderEntity;
import com.chotot.doantotnghiep.service.impl.ICategoryService;
import com.chotot.doantotnghiep.service.impl.IOrderService;
import com.chotot.doantotnghiep.service.impl.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.YearMonth;
import java.util.List;

@Controller
public class HistoryController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IProductService productService;

    @GetMapping("/history")
    public String home(Model model){
        List<OrderDto> list = orderService.findAllByBuyer();
        model.addAttribute("listOrder",list);
        model.addAttribute("pageTitle", "Đơn hàng");
        return "history";
    }

    @GetMapping("/delete-order/{id}")
    public String delete(@PathVariable("id")Long id){
        if(orderService.delete(id)){
            return "redirect:/";
        }
        else {
            return "redirect:/manage-product";
        }
    }
}

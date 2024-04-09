package com.chotot.doantotnghiep.controller.admin;

import com.chotot.doantotnghiep.dto.OrderDto;
import com.chotot.doantotnghiep.service.impl.IOrderService;
import com.chotot.doantotnghiep.service.impl.IProductService;
import com.chotot.doantotnghiep.service.impl.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IProductService productService;

    @RequestMapping({"/home",""})
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
        if (orderService.updateStt(id)) {
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
        return "admin/pay";
    }

    @RequestMapping("/statistics")
    public String statistics(Model model){
        List<OrderDto> history = orderService.history();
        double sum = 0.0;
        for(OrderDto dto : history){
            sum += dto.getTotalPrice();
        }
        Locale vietnamLocale = new Locale("vi", "VN");
        NumberFormat vietnamFormat = NumberFormat.getCurrencyInstance(vietnamLocale);
        String fomat = vietnamFormat.format(sum*0.1);
        model.addAttribute("profit", fomat);

        Long countMem = userService.getAccountTrueCount();
        model.addAttribute("countMem", countMem);

        Long countProduct = productService.countByStatus(0);
        model.addAttribute("countP", countProduct);

        Long countOrderSuccess = productService.countByStatus(7);
        model.addAttribute("countOrderSuccess", countOrderSuccess);


        double monthlyProfit = orderService.monthlyProfit(4);
        String monthlyProfit1 = vietnamFormat.format(monthlyProfit);
        model.addAttribute("monthlyProfit", monthlyProfit1);
        return "admin/statistics";
    }

    @RequestMapping("/history")
    public String history(Model model){
        List<OrderDto> history = orderService.history();
        model.addAttribute("history",history);
        return "admin/history";
    }

    @GetMapping("/cancel/{id}")
    public String delete(@PathVariable("id")Long id){
        if(orderService.delete(id)){
            return "redirect:/admin";
        }
        else {
            return "redirect:/manage-product";
        }
    }

}
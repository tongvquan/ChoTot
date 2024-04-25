package com.chotot.doantotnghiep.controller.admin;

import com.chotot.doantotnghiep.dto.CategoryDto;
import com.chotot.doantotnghiep.dto.OrderDto;
import com.chotot.doantotnghiep.dto.UserDto;
import com.chotot.doantotnghiep.entity.SlideEntity;
import com.chotot.doantotnghiep.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ISlideService slideService;

    @Autowired
    private IStorageService storageService;

    @RequestMapping({"/home",""})
    public String home(Model model){
        List<OrderDto> confirm = orderService.confirm();
        model.addAttribute("confirm",confirm);
        for(OrderDto orderDto : confirm){
            String phone =  orderDto.getProduct().getSeller().getPhoneNumber();
            model.addAttribute("phone", phone);
        }
        model.addAttribute("pageTitle", "Chờ xác nhận");
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
        model.addAttribute("pageTitle", "Đã giao xong");
        return "admin/success";
    }

    @RequestMapping("/pay")
    public String pay(Model model){
        List<OrderDto> pay = orderService.pay();
        model.addAttribute("pay",pay);
        model.addAttribute("pageTitle", "Chờ thanh toán");
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

        model.addAttribute("pageTitle", "Thống kê");
        return "admin/statistics";
    }

    @RequestMapping("/history")
    public String history(Model model){
        List<OrderDto> history = orderService.history();
        model.addAttribute("history",history);
        model.addAttribute("pageTitle", "Lịch sử hoàn thành");
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

    @RequestMapping("/category")
    public String category(Model model){
        List<CategoryDto> categoryDto = categoryService.getAll();
        model.addAttribute("categories",categoryDto);
        model.addAttribute("pageTitle", "Quản lý danh mục");
        CategoryDto category = new CategoryDto();
        model.addAttribute("category", category);
        return "admin/category";
    }

    @PostMapping("/add-category")
    public String save(@ModelAttribute("category") CategoryDto categoryDto){

        if (categoryService.save(categoryDto)){
            return "redirect:/admin/category";
        }
        else {
            return "redirect:/admin";
        }
    }

    @RequestMapping("/slide")
    public String slide(Model model){
        List<SlideEntity> slides = slideService.findAll();
        model.addAttribute("slides",slides);
        model.addAttribute("pageTitle", "Quản lý slide");
        SlideEntity slide = new SlideEntity();
        model.addAttribute("slide", slide);
        return "admin/slide";
    }

    @PostMapping("/add-slide")
    public String create(@ModelAttribute("slide") SlideEntity slideEntity, @RequestParam("inputImage") MultipartFile file){

        this.storageService.store(file);
        String fileName = file.getOriginalFilename();
        slideEntity.setImage(fileName);
        if(slideService.save(slideEntity)){
            return "redirect:/admin/slide";
        }
        else {
            return "redirect:/admin";
        }
    }

    @GetMapping("/delete-slide/{id}")
    public String deleteSlide(@PathVariable("id")Long id){
        if(slideService.delete(id)){
            return "redirect:/admin/slide";
        }
        else {
            return "redirect:/manage-product";
        }
    }



}